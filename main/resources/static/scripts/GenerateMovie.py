import random
import urllib.request
import cv2
import numpy as np
import sys
import os

# restApi = ['http://localhost:8080/api/v1/user-profile/images/download/0',
#            'http://localhost:8080/api/v1/user-profile/images/download/1',
#            'http://localhost:8080/api/v1/user-profile/images/download/2',
#            'http://localhost:8080/api/v1/user-profile/images/download/3',
#            'http://localhost:8080/api/v1/user-profile/images/download/4']


frames = []


def fetchFromApi(url):
    with urllib.request.urlopen(url) as resp:
        # read image as an numpy array
        image = np.asarray(bytearray(resp.read()), dtype="uint8")

        # use imdecode function
        image = cv2.imdecode(image, cv2.IMREAD_COLOR)

    return image


class Image:
    def __init__(self, image, time=500, size=500):
        self.size = size
        self.time = time
        self.shifted = 0.0
        self.img = image
        self.height, self.width, _ = self.img.shape
        if self.width < self.height:
            self.height = int(self.height * size / self.width)
            self.width = size
            self.img = cv2.resize(self.img, (self.width, self.height))
            self.shift = self.height - size
            self.shift_height = True
        else:
            self.width = int(self.width * size / self.height)
            self.height = size
            self.shift = self.width - size
            self.img = cv2.resize(self.img, (self.width, self.height))
            self.shift_height = False
        self.delta_shift = self.shift / self.time

    def reset(self):
        if random.randint(0, 1) == 0:
            self.shifted = 0.0
            self.delta_shift = abs(self.delta_shift)
        else:
            self.shifted = self.shift
            self.delta_shift = -abs(self.delta_shift)

    def get_frame(self):
        if self.shift_height:
            roi = self.img[int(self.shifted):int(self.shifted) + self.size, :, :]
        else:
            roi = self.img[:, int(self.shifted):int(self.shifted) + self.size, :]
        self.shifted += self.delta_shift
        if self.shifted > self.shift:
            self.shifted = self.shift
        if self.shifted < 0:
            self.shifted = 0
        return roi


def renderMovie():
    cnt = 0
    images = []

    for imageUrl in restApi:
        currentImage = Image(fetchFromApi(imageUrl))
        images.append(currentImage)
        if cnt > 300:
            break

        cnt += 1

    prev_image = images[0]
    prev_image.reset()
    for j in range(len(restApi) - 1):
        while True:
            img = images[(j + 1) % len(restApi)]
            if img != prev_image:
                break

        img.reset()
        for i in range(100):
            alpha = i / 100
            beta = 1.0 - alpha
            dst = cv2.addWeighted(img.get_frame(), alpha, prev_image.get_frame(), beta, 0.0)

            frames.append(dst)

        prev_image = img


def generateVideo(inputFrames,
                  videoName="output.avi",
                  videoFolder="C:\\Users\\danit\\OneDrive\\Desktop\\",
                  fps=15):
    videoFullPath = videoFolder + videoName

    frame = inputFrames[0]

    height, width, layers = frame.shape

    video = cv2.VideoWriter(videoFullPath, 0, fps, (width, height))

    for currentResized in inputFrames:
        video.write(currentResized)

    cv2.destroyAllWindows()
    video.release()


if __name__ == "__main__":
    print("MovieName: " + sys.argv[1])
    print("FolderName: " + sys.argv[2])
    print("FPS: " + sys.argv[3])
    print("Images:\n" + str(sys.argv[3:]))

    restApi = sys.argv[4:]
    movieName = sys.argv[1]
    folderPath = sys.argv[2]
    fps = sys.argv[3]

    if os.path.exists(folderPath + movieName):
        os.remove(folderPath + movieName)

    renderMovie()
    generateVideo(frames, movieName, folderPath, int(fps))

import axios from "axios";


export const requestMovieRender = (movieArgsDto) => {
    return axios.post("http://localhost:8080/api/v1/render/movie-upload", movieArgsDto)
}

export const requestPreview = (videoRenderDto) => {
    return axios.post("http://localhost:8080/api/v1/render/preview-upload", videoRenderDto)
}

export const getAllImages = () => {
    return axios.get("http://localhost:8080/api/v1/user-profile/images/download")
}

export const getAllAudioFile = () => {
    return axios.get("http://localhost:8080/api/v1/user-profile/audio/download")
}

export const deleteFile = (fileId) => {
    return axios.delete(`http://localhost:8080/api/v1/user-profile/images/delete/${fileId}`)
}


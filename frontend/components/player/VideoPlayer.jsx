import React from 'react';

function VideoPlayer({currentUrl}) {
    return (

        <video src={currentUrl}
               width="720px" height="380px" controls preload="none">
        </video>

    )
}

export default VideoPlayer;
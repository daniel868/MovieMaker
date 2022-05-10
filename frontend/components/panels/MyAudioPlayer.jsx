import React, {Component} from 'react';
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';

function MyAudioPlayer({srcFile}) {
    return (
        <AudioPlayer
            src={srcFile}
            onPlay={e => console.log("onPlay")}
        />
    )
}

export default MyAudioPlayer;
import "./App.css"
import React, {useState} from "react";
import {Button, Divider, Drawer, Layout, Select} from 'antd';
import MusicPanel from "./components/panels/MusicPanel";
import PicturePanel from "./components/panels/PicturePanel";
import {Footer, Header} from "antd/es/layout/layout";
import ImageSelect from "./components/panels/ImageSelect";
import {requestPreview} from "./Client"
import VideoComponent from "./components/player/VideoComponent";
import EffectsDrawer from "./components/panels/EffectsDrawer";
import MovieDrawer from "./components/panels/MovieDrawer";

const {Option} = Select;

function App() {
    const [visible, setVisible] = useState(false)
    const [effectsDrawerVisible, setEffectsDrawerVisible] = useState(false)
    const [movieDrawerVisible, setMovieDrawerVisible] = useState(false)

    const [renderMovie, setRenderMovie] = useState(false)
    const [movieUrl, setMovieUrl] = useState('')

    const [data, setData] = useState([])
    const [imageUsed, setImageUsed] = useState([])


    const onClose = () => {
        setVisible(false)
    }

    const startProgressBar = React.useRef(null)

    return (
        <>
            <Drawer
                title="Media Panel"
                placement="left"
                onClose={onClose}
                visible={visible}
                size={"large"}
            >
                <PicturePanel onDataChange={(parseData) => {
                    setData(parseData)
                }}/>

                <Divider orientation={"left"} plain>Media sounds here</Divider>
                <MusicPanel/>
            </Drawer>

            <EffectsDrawer
                isVisible={effectsDrawerVisible}
                close={() => {
                    setEffectsDrawerVisible(false)
                }}
                imageUsed={imageUsed}
                startProgressBar={startProgressBar.current}
            />

            <MovieDrawer
                isVisible={movieDrawerVisible}
                onClose={() => {
                    setMovieDrawerVisible(false)
                }}
                images={imageUsed}
                startProgressBar={startProgressBar.current}
                setMovieUrl={(movieUrl) => {
                    setMovieUrl(movieUrl)
                }}
                setRenderMovie={(renderMovie) => {
                    setRenderMovie(renderMovie)
                }}
            />

            <Layout className="layout">
                <Header>
                    <Button type="primary" onClick={() => {
                        setVisible(true)
                    }}>
                        Open Media Panel
                    </Button>
                    <Button type="primary" style={{marginLeft: "10px"}} onClick={() => {
                        setEffectsDrawerVisible(true)
                    }}>
                        Open Effects Panel
                    </Button>

                    <Button type="primary" style={{marginLeft: "10px"}} onClick={() => {
                        setMovieDrawerVisible(true)
                    }}>
                        Open Movie Render
                    </Button>
                </Header>

                <Layout className="layout" style={{marginTop: "5px"}}>
                    <VideoComponent startRenderProgress={startProgressBar}
                                    renderMovie={renderMovie}
                                    movieUrl={movieUrl}/>
                    <Footer>
                        <ImageSelect imageData={data}
                                     onImageAdded={(updatedList) => {
                                         setImageUsed(updatedList)
                                         setRenderMovie(false)
                                     }}
                                     onImageDeleted={(imageDeleted) => {
                                         setImageUsed(imageUsed.filter(currentImage =>
                                             currentImage.uid !== imageDeleted.uid))
                                     }}
                        />
                    </Footer>
                </Layout>
            </Layout>

        </>


    )
}

export default App;

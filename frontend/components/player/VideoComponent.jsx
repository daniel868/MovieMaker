import React, {useState} from 'react';
import "antd/dist/antd.css";
import {Layout, Progress, Row} from "antd";
import VideoPlayer from "./VideoPlayer";
import {Content} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";

function VideoComponent({startRenderProgress, renderMovie, movieUrl}) {
    const [currentUrl, setUrl] = useState('http://localhost:8080/api/v1/render/preview-download')

    const [uploadPercentage, setUploadPercentage] = useState(0);
    const [allowUpload, setAllowUpload] = useState(true);
    const [visible, setVisible] = useState(false)

    React.useEffect(() => {
        startRenderProgress.current = startUploadFile
    }, [])


    const startUploadFile = () => {
        console.log("Command to start new render")
        setVisible(true)
        const data = new FormData();
        let url = "http://localhost:8080/api/v1/render/start-progress"
        const eventSource = new EventSource("http://localhost:8080/api/v1/render/current-progress");
        let guidValue = null;

        eventSource.addEventListener("GUI_ID", (event) => {
            guidValue = JSON.parse(event.data);
            console.log(`Guid from server: ${guidValue}`);
            data.append("guid", guidValue);
            eventSource.addEventListener(guidValue, (event) => {
                const result = JSON.parse(event.data);
                if (uploadPercentage !== result) {
                    setUploadPercentage(result);
                }
                if (result === "100") {
                    eventSource.close();
                }

            });
            sentToServer(url, data);
        });

        eventSource.onerror = (event) => {
            if (event.target.readyState === EventSource.CLOSED) {
                console.log("SSE closed (" + event.target.readyState + ")");
            }
            setUploadPercentage(0);
            eventSource.close();
        };

        eventSource.onopen = () => {
            console.log("connection opened");
        };
    };

    const sentToServer = (url, data) => {

        const requestOptions = {
            method: "POST",
            mode: "no-cors",
            body: data,
        };
        fetch(url, requestOptions).then(() => setAllowUpload(true));
    };

    return (
        <>
            <Layout>
                {
                    visible === true ?
                        <Sider style={{backgroundColor: "#ffffff", marginRight: "30px", height: "0px"}}>
                            <Row justify="center" style={{marginTop: "70px"}}>
                                <Progress type="circle" percent={(uploadPercentage / 100) * 100}/>
                            </Row>
                            <br></br>
                            <br/>
                        </Sider>
                        : null
                }
            </Layout>
            <Layout className="layout" style={{marginTop: "20px"}}>
                <Content style={{padding: '0 450px'}}>
                    {
                        renderMovie === true ?
                            <VideoPlayer currentUrl={movieUrl}/> :
                            uploadPercentage === 100  ?
                                <VideoPlayer currentUrl={currentUrl}/> :
                                <VideoPlayer currentUrl={'http://localhost:8080/api/v1/streaming/video/'}/>
                    }
                </Content>
            </Layout>

        </>
    );
}

export default VideoComponent;
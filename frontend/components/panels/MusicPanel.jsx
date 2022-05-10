import React, {useEffect, useState} from 'react';
import {Layout, Spin, Upload} from 'antd';
import {getAllAudioFile} from "../../Client";
import MyAudioPlayer from "./MyAudioPlayer";
import UploadComponent from "../../Utils/UploadComponent";
import {Empty} from 'antd';
import {LoadingOutlined} from "@ant-design/icons";


const Footer = Layout.Footer;

const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>

const UPLOAD_ENDPOINT = "http://localhost:8080/api/v1/user-profile/audio/upload"

function MusicPanel() {

    const [fileList, setFileList] = useState([])
    const [currentAudioFile, setCurrentAudioFile] = useState("")
    const [fetching, setFetching] = useState(true)

    const uploadListOption = {
        showDownloadIcon: true,
        showRemoveIco: true,
        downloadIcon: 'download'
    }

    const getAvailableData = () => {

        getAllAudioFile().then((response) => {
            const list = []

            console.log(response.data)

            response.data.forEach(data => {
                const object = {
                    'name': data.fileName,
                    'url': data.fileUrl
                }
                list.push(object)
            })
            setFileList(list)
        }).catch((error) => {
            console.log(error)
        }).finally(() => {
            setFetching(false)
        })
    }

    const onRemove = (file) => {
        const index = fileList.indexOf(file)
        const newFileList = fileList.slice()

        newFileList.splice(index, 1)
        setFileList(newFileList)

    }

    const handleUpload = ({fileList}) => {
        setFileList(fileList)
    }

    const fetchAudio = () => {
        if (fetching) {
            return <Spin indicator={antIcon}/>
        } else
            return (
                <Upload
                    onChange={handleUpload}
                    onRemove={onRemove}
                    showUploadList={
                        uploadListOption
                    }
                    onPreview={(currentFile) => {
                        setCurrentAudioFile(currentFile.url)
                    }}

                    fileList={fileList}>
                </Upload>
            )

    }


    useEffect(() => {
        getAvailableData()
    }, [])


    return (
        <>
            <Layout>
                < Layout style={{
                    width: "600px",
                    height: "240px",
                    overflow: "auto",
                }}>
                    {
                        fileList.length === 0 ?
                            <Empty/>
                            :
                            fetchAudio()
                    }
                </Layout>
                <Footer style={{textAlign: "center", backgroundColor: "#ffffff"}}>
                    <MyAudioPlayer
                        srcFile={currentAudioFile}/>

                    <UploadComponent
                        UPLOAD_ENDPOINT={UPLOAD_ENDPOINT}
                        fetchData={() => {
                            getAvailableData()
                        }}
                        setFetch={(fetchStatus) => {
                            setFetching(fetchStatus)
                        }}
                    />
                </Footer>
            </Layout>
        </>
    )
}

export default MusicPanel;
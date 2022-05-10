import React, {Component, useEffect, useState} from 'react';
import {deleteFile, getAllImages} from "../../Client";
import Layout from "antd/es/layout/layout";
import {Empty} from 'antd';
import {Modal, Upload} from "antd";
import UploadComponent from "../../Utils/UploadComponent";
import {Spin} from 'antd';
import {LoadingOutlined} from '@ant-design/icons';


const antIcon = <LoadingOutlined style={{fontSize: 24}} spin/>

const UPLOAD_ENDPOINT = "http://localhost:8080/api/v1/user-profile/image/upload"

function PicturePanel({onDataChange}) {
    const [previewVisible, setPreviewVisible] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const [previewTitle, setPreviewTitle] = useState('')

    const [fileList, setFileList] = useState([])
    const [fetching, setFetching] = useState(true)

    const loadAvailableImages = () => {
        setFetching(true)
        getAllImages().then(result => {

            setFileList([])
            const tempList = []

            console.log(result.data)
            result.data.forEach(data => {
                const object = {
                    'name': data.fileName,
                    'url': data.fileUrl,
                    'id': data.id
                }
                tempList.push(object)
            })

            setFileList(tempList)
            onDataChange(tempList)

        }).catch((error) => {
            console.log(error)
        }).finally(() => {
            setFetching(false)
        })
    }

    const handleDeleteFile = (file) => {
        console.log("File to delete " + file)

        deleteFile(file.id).then((result) => {
            console.log(result.data)
        })
    }


    const handleCancel = () => {
        setPreviewVisible(false)
    }

    const handlePreview = async (file) => {
        setPreviewImage(file.url || file.preview)
        setPreviewVisible(true)
        setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf('/') + 1))
    }

    const handleChange = (info) => {
        setFileList(info.fileList)
    }

    const fetchImages = () => {
        if (fetching) {
            return <Spin indicator={antIcon}/>
        } else
            return (
                <Upload
                    listType="picture-card"
                    fileList={fileList}
                    onChange={(info) => {
                        handleChange(info)
                    }}
                    onPreview={handlePreview}
                    onRemove={(file) => {
                        handleDeleteFile(file)
                    }}
                >
                </Upload>
            )
    }

    useEffect(() => {
        loadAvailableImages()
    }, [])


    return (
        <>
            < Layout style={{
                width: "660px",
                height: "240px",
                overflow: "auto",
            }}>
                {
                    fileList.length === 0 ?
                        <Empty/>
                        :
                        fetchImages()
                }
                <Modal
                    visible={previewVisible}
                    title={previewTitle}
                    footer={null}
                    onCancel={handleCancel}
                >
                    <img alt="example" style={{width: '100%'}} src={previewImage}/>
                </Modal>
            </Layout>

            <UploadComponent
                UPLOAD_ENDPOINT={UPLOAD_ENDPOINT}
                fetchData={() => {
                    loadAvailableImages()
                }}
                setFetch={(fetchValue) => {
                    setFetching(fetchValue)
                }}
            />
        </>
    )

}

export default PicturePanel;
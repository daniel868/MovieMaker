import React, {Component, useState} from 'react';
import {Modal, Select, Upload} from 'antd';
import Layout, {Content} from "antd/es/layout/layout";

const {Option} = Select;

function ImageSelect({imageData, onImageAdded,onImageDeleted}) {
    const [previewVisible, setPreviewVisible] = useState(false)
    const [previewImage, setPreviewImage] = useState('')
    const [previewTitle, setPreviewTitle] = useState('')

    const [fileList, setFileList] = useState([])


    const handleChange = (value) => {
        console.log(`selected ${value}`);

        const tempList = []

        fileList.forEach(currentValue => {
            tempList.push(currentValue)
        })
        const newObject = {
            'url': value,
            'index': value.substring(value.lastIndexOf('/') + 1)
        }
        tempList.push(newObject)

        setFileList(tempList)

        onImageAdded(tempList)

        console.log(tempList)
    }


    const handlePreview = async (file) => {
        setPreviewImage(file.url || file.preview)
        setPreviewVisible(true)
        setPreviewTitle(file.name || file.url.substring(file.url.lastIndexOf('/') + 1))
    }

    const handleFileUpload = (info) => {
        setFileList(info.fileList)
    }
    const handleCancel = () => {
        setPreviewVisible(false)
    }

    return (
        <>
            <Select defaultValue="Choose image from the list" style={{width: 160, textAlign: "center"}}
                    onChange={handleChange}>
                {
                    imageData.map((currentValue) => {
                        return <Option key={currentValue.uid}
                                       value={currentValue.url}>{currentValue.name}</Option>
                    })
                }
            </Select>

            < Layout style={{
                marginTop: "15px",
                height: "190px",
                overflow: "auto",
                backgroundColor: "#c5d5de"
            }}>
                <Upload
                    listType="picture-card"
                    fileList={fileList}
                    onPreview={handlePreview}
                    onChange={(info) => {
                        handleFileUpload(info)
                    }}
                    onRemove={(deletedFile) => {
                        console.log(deletedFile)
                        console.log(fileList.filter(currentFile => currentFile.uid !== deletedFile.uid))
                        onImageDeleted(deletedFile)
                    }}
                >
                </Upload>
                <Modal
                    visible={previewVisible}
                    title={previewTitle}
                    footer={null}
                    onCancel={handleCancel}
                >
                    <img alt="example" style={{width: '100%'}} src={previewImage}/>
                </Modal>
            </Layout>
        </>


    )
        ;


}

export default ImageSelect;
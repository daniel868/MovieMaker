import React, {useState} from 'react';
import {Button, Drawer, Form, Input, Select} from "antd";
import {requestMovieRender} from "../../Client";

const {Option} = Select;

function MovieDrawer({
                         isVisible, onClose, images, startProgressBar,
                         setMovieUrl,
                         setRenderMovie
                     }) {
    const [folderPath, setFolderPath] = useState('C:\\Users\\danit\\OneDrive\\Desktop\\')
    const [videoName, setVideoName] = useState('')
    const [fps, setFps] = useState('15')

    const fpsList = ['15', '30']


    const handleFpsChange = (newFps) => {
        console.log(newFps)
        setFps(newFps)
    }

    const onFinish = (values) => {
        setVideoName(values.filename)

        const imagesUrl = []

        images.forEach(currentImage => {
            imagesUrl.push(currentImage.url)
        })

        const videoDto = {
            'videoFileName': values.filename + '.mp4',
            'videoFolderPath': folderPath,
            'fps': fps,
            'imagesUrl': imagesUrl
        }
        console.log(videoDto)

        requestMovieRender(videoDto).then((result) => {
            console.log(result.data)
            const movieUrl = `http://localhost:8080/api/v1/streaming/video/${values.filename}`
            setMovieUrl(movieUrl)
            setRenderMovie(true)
        })
        startProgressBar()
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <>
            <Drawer
                title={"Movie Drawer"}
                visible={isVisible}
                onClose={onClose}
                width={500}
            >

                <Form
                    name="basic"
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    initialValues={{remember: true}}
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Filename"
                        name="filename"
                        rules={[{required: true, message: 'Please input your video filename!'}]}
                    >
                        <Input/>
                    </Form.Item>

                    <Form.Item label="Fps">
                        <Select defaultValue={fpsList[0]}
                                onChange={handleFpsChange}
                                style={{width: 120}}>
                            {
                                fpsList.map(currentValue => {
                                    return <Option key={currentValue}>{currentValue}</Option>
                                })
                            }
                        </Select>
                    </Form.Item>

                    <Form.Item wrapperCol={{offset: 8, span: 16}}>
                        <Button type="primary" htmlType="submit">
                            Start render
                        </Button>
                    </Form.Item>
                </Form>


            </Drawer>
        </>
    );
}

export default MovieDrawer;
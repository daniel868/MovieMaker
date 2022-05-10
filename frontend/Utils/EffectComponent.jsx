import React, {Component, useState} from 'react';
import {Option} from "antd/es/mentions";
import {Button, Layout, Select} from "antd";
import {Content} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import {requestPreview} from "../Client";

function EffectComponent({sourceImageIndex, destinationImageIndex, startProgressBar}) {
    const [effect, setEffect] = useState('')

    const availableEffects = [
        <Option key={"ScrollRightEffect"}>{"ScrollRightEffect"}</Option>,
        <Option key={"ScrollLeftEffect"}>{"ScrollLeftEffect"}</Option>,
        <Option key={"PixelDissolveEffect"}>{"ScrollUpDown"}</Option>,
        <Option key={"AlphaDissolveEffect"}>{"ScrollDownUp"}</Option>
    ];

    const handleChange = (value) => {
        console.log(`Selected: ${value}`);
        setEffect(value)
    }

    const requestPreviewEffect = () => {

        let videoRenderDto = {
            effectType: effect,
            sourceIndex: sourceImageIndex,
            destinationIndex: destinationImageIndex
        }

        requestPreview(videoRenderDto).then((result) => {
            console.log(result.data)
        }).catch((error) => {
            console.log(error)
        })

        startProgressBar()

    }
    return (
        <Layout style={{backgroundColor: "#c5d5de"}}>
            <Content style={{padding: "10px"}}>
                <Select size={"default"} defaultValue={"Pick effect"}
                        onChange={handleChange}
                        style={{width: 200}}>
                    {availableEffects}
                </Select>
            </Content>
            <Sider style={{padding: "10px", backgroundColor: "#c5d5de"}}>
                <Button
                    onClick={() => requestPreviewEffect()}
                    style={{backgroundColor: "#0d3952"}}
                    type={"primary"}>Preview effect</Button>
            </Sider>
        </Layout>

    );

}

export default EffectComponent;
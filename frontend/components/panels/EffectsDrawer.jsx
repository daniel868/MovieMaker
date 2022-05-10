import React, {Component} from 'react';
import {Button, Drawer, Layout, Select} from "antd";
import EffectComponent from "../../Utils/EffectComponent";

function EffectsDrawer({isVisible, close, imageUsed, startProgressBar}) {
    const effects = [];
    if (imageUsed.length > 1) {
        for (let i = 0; i < imageUsed.length; i++) {
            if (i + 1 <= imageUsed.length - 1) {
                effects.push(
                    <EffectComponent sourceImageIndex={parseInt(imageUsed[i].index)}
                                     destinationImageIndex={parseInt(imageUsed[i + 1].index)}
                                     startProgressBar={startProgressBar}
                    />
                )
            }
        }
    }
    return (
        <>
            <Drawer
                title={"Effects Drawer"}
                placement={"right"}
                onClose={close}
                visible={isVisible}
                width={500}>
                {
                    effects
                }
            </Drawer>

        </>
    );

}

export default EffectsDrawer;
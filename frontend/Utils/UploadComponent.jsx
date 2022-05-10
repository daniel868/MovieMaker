import React, {useState} from "react";
import axios from "axios";
import {Button, Input} from "antd";

function UploadComponent({fetchData, UPLOAD_ENDPOINT, setFetch}) {
    const [file, setFile] = useState(null);

    const handleFileUpload = async e => {
        e.preventDefault()

        setFetch(true)

        await uploadFile(file).then((res) => {
            fetchData()
        })
    }

    const uploadFile = async file => {
        const formData = new FormData()
        formData.append('file', file)

        return await axios.post(UPLOAD_ENDPOINT, formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }).catch((error) => {
            console.log(error)
        })
    }

    const handleOnChange = e => {
        console.log(e.target.files[0]);
        setFile(e.target.files[0]);
    };
    return (
        <div style={{textAlign: "center", marginTop: "20px"}}>
            <Input style={{width: "250px", height: "40px", marginRight: "20px"}}
                   type="file"
                   onChange={handleOnChange}/>
            <Button style={{height: "40px"}} type="submit" onClick={handleFileUpload}>Upload File</Button>
        </div>
    )
}

export default UploadComponent;
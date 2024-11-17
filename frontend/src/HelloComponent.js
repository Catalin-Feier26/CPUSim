
import React, { useEffect, useState}  from "react";
import { getHelloMessage} from "./apiService";

const HelloComponent = () => {
    const [message, setMessage] = useState("");

    useEffect(() => {
        const fetchHelloMessage = async () => {
            try {
                console.log("Fetching the hello message");
                const message = await getHelloMessage();
                console.log("Received the hello message: ", message);
                setMessage(message);
            } catch (error) {
                console.error("Error fetching the hello message: ", error);
                setMessage("Error fetching the hello message");
            }
        };

        fetchHelloMessage();
    }, []);
    return (
        <div>
            <h1>Hello</h1>
            <p>{message}</p>
        </div>
    );
};

export default HelloComponent;
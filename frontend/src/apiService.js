import axios from "axios";

const API_URL = "http://localhost:8081/api";

export const getHelloMessage = async () => {
    try {
        console.log(`Making request to ${API_URL}/hello`);
        const response = await axios.get(`${API_URL}/hello`);
        console.log("Received response:", response.data);
        return response.data;
    } catch (error) {
        console.error("Error fetching the hello message: ", error.response ? error.response.data : error.message);
        throw error;
    }
}
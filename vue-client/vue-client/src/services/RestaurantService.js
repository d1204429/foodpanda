import axios from 'axios';

const API_URL = 'http://localhost:1988/api/restaurants';

// Create an axios instance with default config
const axiosInstance = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  }
});

class RestaurantService {
  getAllRestaurants() {
    return axiosInstance.get(API_URL);
  }

  getRestaurantById(id) {
    return axiosInstance.get(`${API_URL}/${id}`);
  }

  searchRestaurants(keyword) {
    return axiosInstance.get(`${API_URL}/name/${keyword}`);
  }

  addRestaurant(restaurant) {
    return axiosInstance.post(API_URL, restaurant);
  }

  updateRestaurant(id, restaurant) {
    return axiosInstance.put(`${API_URL}/${id}`, restaurant);
  }

  deleteRestaurant(id) {
    return axiosInstance.delete(`${API_URL}/${id}`);
  }
}

export default new RestaurantService();
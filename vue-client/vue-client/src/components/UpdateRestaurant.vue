<template>
  <div>
    <h1>修改餐廳</h1>
    <form @submit.prevent="updateRestaurant">
      <input v-model="restaurant.name" placeholder="餐廳名稱" required />
      <input v-model="restaurant.address" placeholder="地址" required />
      <button type="submit">修改</button>
    </form>
  </div>
</template>

<script>
import RestaurantService from '@/services/RestaurantService';

export default {
  data() {
    return {
      restaurant: {
        restaurant_id: '',
        name: '',
        address: ''
      }
    };
  },
  methods: {
    loadRestaurant(id) {
      RestaurantService.getRestaurantById(id)
      .then(response => {
        this.restaurant = response.data;
      })
      .catch(error => {
        console.error("Error loading restaurant:", error);
      });
    },
    updateRestaurant() {
      RestaurantService.updateRestaurant(this.restaurant.restaurant_id, this.restaurant)
      .then(() => {
        alert("餐廳修改成功！");
      })
      .catch(error => {
        console.error("Error updating restaurant:", error);
      });
    }
  },
  mounted() {
    const restaurantId = this.$route.params.id; // 從路由取得餐廳ID
    this.loadRestaurant(restaurantId);
  }
};
</script>

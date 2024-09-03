import { createRouter, createWebHistory } from 'vue-router';
import RestaurantList from '@/components/RestaurantList.vue';
import AddRestaurant from '@/components/AddRestaurant.vue';
import UpdateRestaurant from '@/components/UpdateRestaurant.vue';

const routes = [
  { path: '/', component: RestaurantList },
  { path: '/add', component: AddRestaurant },
  { path: '/update/:id', component: UpdateRestaurant }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
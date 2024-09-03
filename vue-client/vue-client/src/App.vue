<template>
  <div id="app">
    <router-link to="/">餐廳列表</router-link>
    <router-link to="/add">新增餐廳</router-link>
    <!-- router-view 將顯示對應路由的內容 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
/* 加一些樣式讓連結更明顯 */
router-link {
  margin-right: 15px;
  color: blue;
  text-decoration: underline;
  cursor: pointer;
}
</style>

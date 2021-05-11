<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
      <v-btn @click="addUser">Add User</v-btn>
<!--      <v-btn @click="go2booksView">► Books</v-btn>-->
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="users"
        :search="search"
        @click:row="editUser"
        @refresh="refreshList"
    ></v-data-table>

    <UserDialog
        :opened="dialogVisible"
        :user="selectedUser"
        @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";

import UserDialog from "../components/UserDialog";
//import router from "@/router";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: true,
          value: "username",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    editUser(user){
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser(){
      this.dialogVisible = true;
    },
    async refreshList(){
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  created() {
    this.refreshList();
  },
  mounted() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNewUser ? "Create user" : "Edit user" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Password" type="password"/>
          <v-radio-group v-model="user.roles">
            <v-radio label="Secretary" value="SECRETARY" />
            <v-radio label="Doctor" value="DOCTOR" />
            <v-radio label="Admin" value="ADMIN" />
          </v-radio-group>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNewUser ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNewUser" @click="deleteUser">Delete User</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNewUser) {
        api.users
            .create({
              username: this.user.username,
              email: this.user.email,
              password: this.user.password,
              roles: [this.user.roles]
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.users
            .edit(this.user.id,{
              id: this.user.id,
              username: this.user.username,
              email: this.user.email,
              password: this.user.password,
              roles: [this.user.roles],
            })
            .then(() => this.$emit("refresh"));
      }
    },
    deleteUser(){
      api.users.deleteById(this.user.id)
          .then((response) => {
            if (response)
              this.$emit("refresh")
          });
    }
  },
  computed: {
    isNewUser: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>

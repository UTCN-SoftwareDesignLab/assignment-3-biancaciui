import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
// import ItemList from "../views/ItemList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import ConsultationList from "../views/ConsultationList";
// import PatientList from "../views/PatientList";
import ConsultationDoctorList from "../views/ConsultationDoctorList";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/consultations",
    name: "Doctors",
    component: ConsultationDoctorList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isDoctor) {
        next();
      } else {
        next({ name: "Doctor's Consultations" });
      }
    },
  },

  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "User" });
      }
    },
  },
  {
    path: "/consultations",
    name: "Consultations",
    component: ConsultationList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isSecretary) {
        next();
      } else {
        next({ name: "Consultation" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;

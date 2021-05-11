<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
      <v-btn @click="addPatient">Add Patient</v-btn>
      <v-btn @click="go2consultationView">► Consultations</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="patients"
        :search="search"
        @click:row="editPatient"
        @refresh="refreshList"
    ></v-data-table>

    <PatientDialog
        :opened="dialogVisible"
        :patient="selectedPatient"
        @refresh="refreshList"
    ></PatientDialog>
  </v-card>
</template>

<script>
import api from "../api";

import PatientDialog from "../components/PatientDialog";
import router from "@/router";

export default {
  name: "PatientDialog.vue",
  components: { PatientDialog },
  data() {
    return {
      patients: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: true,
          value: "name",
        },
        { text: "Numerical Code:", value: "numericalCode" },
        { text: "Identity Card #", value: "identityCardNr" },
        { text: "Date of Birth", value: "dateOfBirth" },
        { text: "Address", value: "address" },
      ],
      dialogVisible: false,
      selectedPatient: {},
    };
  },
  methods: {
    editPatient(patient){
      this.selectedPatient = patient;
      this.dialogVisible = true;
    },
    addPatient(){
      this.dialogVisible = true;
    },
    async refreshList(){
      this.dialogVisible = false;
      this.selectedPatient = {};
      this.patients = await api.patients.allPatients();
    },
    go2consultationView(){
      router.push("/consultations");
    },
  },
  created() {
    this.refreshList();
  },
  mounted() {
    this.refreshList();
  },
}
</script>



<style scoped>

</style>
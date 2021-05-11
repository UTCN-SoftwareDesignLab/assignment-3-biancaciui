<template>
  <v-card>
    <v-card-title>
      Consultations
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
      <v-btn @click="addConsultation">Add Consultation</v-btn>
<!--      <v-btn v-if="isSecretary" :disabled="isSecretary" @click="go2patientsView" >► Patients</v-btn>-->
      <v-btn v-show="isSecretary" @click="go2patientsView" >► Patients</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="consultations"
        :search="search"
        @click:row="editConsultation"
        @refresh="refreshList"
    ></v-data-table>

    <ConsultationDialog
        :opened="dialogVisible"
        :consultation="selectedConsultation"
        @refresh="refreshList"
        @close="closeDialog"
    ></ConsultationDialog>
  </v-card>
</template>

<script>
import api from "../api";
// import PatientDialog from "../components/PatientDialog";

import ConsultationDialog from "../components/ConsultationDialog";
import router from "@/router";

export default {
  name: "ConsultationList.vue",
  components: { ConsultationDialog },
  data() {
    return {
      consultations: [],
      doctorList: [],
      search: "",
      headers: [
        {
          text: "ID",
          align: "start",
          sortable: true,
          value: "id",
        },
        { text: "Patient's Name:", value: "patientName" },
        { text: "Doctor's Name:", value: "doctorName" },
        { text: "Date:", value: "date" },
        { text: "Details", value: "details" },
      ],
      dialogVisible: false,
      selectedConsultation: {},
    };
  },
  methods: {
    editConsultation(consultation){
      this.selectedConsultation = consultation;
      this.dialogVisible = true;
    },
    addConsultation(){
      this.dialogVisible = true;
    },
    async refreshList(){
      this.dialogVisible = false;
      this.selectedConsultation = {};
      this.consultations = await api.consultations.allConsultations();
    },
    go2patientsView(){
      router.push("/patients");
    },
    closeDialog() {
      this.dialogVisible = false;
      this.refreshList();
    },
  },
  created() {
    this.refreshList();
  },

  mounted() {
    this.refreshList();
  },
  computed: {
    isSecretary(){
      return this.$store.getters["auth/isSecretary"];
    },
  },
}
</script>

<style scoped>

</style>
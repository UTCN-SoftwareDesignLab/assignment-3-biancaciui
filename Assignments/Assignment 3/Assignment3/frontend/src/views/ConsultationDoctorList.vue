<template>
  <v-card>
    <v-card-title>
      Doctor's Consultations
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
<!--      <v-btn @click="addConsultation">Add Consultation</v-btn>-->
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="consultations"
        :search="search"
        @click:row="editDescriptionConsultation"
        @refresh="refreshList"
    ></v-data-table>

    <ConsultationDoctorDialog
        :opened="dialogVisible"
        :consultation="selectedConsultation"
        @refresh="refreshList"
        @close="closeDialog"
    ></ConsultationDoctorDialog>
  </v-card>
</template>

<script>
import api from "../api";
// import PatientDialog from "../components/PatientDialog";

import ConsultationDoctorDialog from "../components/ConsultationDoctorDialog";
// import router from "@/router";

export default {
  name: "ConsultationDoctorList.vue",
  components: { ConsultationDoctorDialog },
  data() {
    return {
      consultations: [],
      //doctorList: [],
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
        { text: "Date:", value: "date"},
        { text: "Details", value: "details" },
      ],
      dialogVisible: false,
      selectedConsultation: {},
    };
  },
  methods: {
    editDescriptionConsultation(consultation){
      this.selectedConsultation = consultation;
      this.dialogVisible = true;
      //this.refreshList();
    },
    addConsultation(){
      this.dialogVisible = true;
    },
    async refreshList(){
      this.dialogVisible = false;
      this.selectedConsultation = {};
      //AICI TRB SA PUN DOAR CONSULTATIILE CE TIN DE DOCTORUL CURENT
      this.consultations = await api.consultations.allConsultations();
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
    //AICI FA SA IEI ID-UL DOCTORULUI
    getDoctorId(){
      return this.$store.getters["auth/isSecretary"];
    },
  },
}
</script>

<style scoped>

</style>
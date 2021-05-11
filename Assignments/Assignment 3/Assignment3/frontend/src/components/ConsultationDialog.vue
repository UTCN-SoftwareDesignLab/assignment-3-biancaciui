<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNewConsultation ? "Create consultation" : "Edit consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="consultation.patientName" label="Patient's Name" />
          <v-text-field v-model="consultation.doctorName" label="Doctor's Name" />

<!--          <v-label >Patient's Name</v-label>-->
<!--          <v-select :items="patientList" @input="selectPatient"></v-select>-->

<!--          <v-label >Doctor's Name</v-label>-->
<!--          <v-select :items="doctorList" @input="selectDoctor"></v-select> -->
<!--&lt;!&ndash;            &ndash;&gt;-->
<!--          <v-label >Patient's Name</v-label>-->
<!--          <v-select :items="patientList" ></v-select>-->

<!--          <v-label >Doctor's Name</v-label>-->
<!--          <v-select :items="doctorList" ></v-select>-->

<!--          <v-select-->
<!--              v-model="selectedDoctor"-->
<!--              v-if="isSecretary && isNewConsultation"-->
<!--              item-text="username"-->
<!--              item-value="id"-->
<!--              :items="refreshDocList"-->
<!--          >-->
<!--          </v-select>-->
          <v-text-field v-model="consultation.date" label="Date" />
          <v-text-field v-model="consultation.details" label="Details" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNewConsultation ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNewConsultation" @click="deleteConsultation">Delete Consultation</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ConsultationDialog.vue",
  props: {
    consultation: Object,
    opened: Boolean,
    // selectedDoctor: Object,
    // selectedPatient: Object,
    doctorList: Array,
    patientList: Array,
  },
  methods: {
    // selectPatient(patient) {
    //   this.selectedPatient = patient;
    // },
    // selectDoctor(doctor) {
    //   this.selectedDoctor = doctor;
    // },

    persist() {
      if (this.isNewConsultation) {
        api.consultations
            .create({
              patientName: this.consultation.patientName,
              patientId: this.consultation.patientId,
              doctorName: this.consultation.doctorName,
              doctorId: this.consultation.doctorId,
              date: this.consultation.date,
              details: this.consultation.details,
            })
            .then(() => this.$emit("refresh"));
      } else {

        //console.log(api.users.findDoctors().length)

        api.consultations
            .edit(this.consultation.id,{
              id: this.consultation.id,
              patientName: this.consultation.patientName,
              patientId: this.consultation.patientId,
              doctorName: this.consultation.doctorName,
              doctorId: this.consultation.doctorId,
              date: this.consultation.date,
              details: this.consultation.details,
            })
            .then(() => this.$emit("refresh"));
      }
      //
      // this.doctorList = api.users.findDoctors();
      // this.patientList = api.patients.allPatients();

    },
    deleteConsultation(){
      api.consultations.deleteById(this.consultation.id)
          .then((response) => {
            if (response)
              this.$emit("refresh")
          });
    }
  },
  computed: {
    isSecretary() {
      return this.$store.getters["auth/isSecretary"];
    },
    isNewConsultation: function () {
      return !this.consultation.id;
    },
    // refreshDocList() {
    //   return api.users.findDoctors();
    // },
  },
  // watch:{
  //   doctorListRefresh(){
  //     this.doctorList = api.users.findDoctors();
  //   },
  // },
  // mounted(){
  //   this.doctorList = api.users.findDoctors();
  // }
}

</script>

<style scoped>

</style>
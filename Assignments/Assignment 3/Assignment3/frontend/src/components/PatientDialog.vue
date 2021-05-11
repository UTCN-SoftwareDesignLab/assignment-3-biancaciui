<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNewPatient ? "Create patient" : "Edit patient" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="patient.name" label="Name" />
          <v-text-field v-model="patient.numericalCode" label="Numerical Code" />
          <v-text-field v-model="patient.identityCardNr" label="Identity Card #" />
          <v-text-field v-model="patient.address" label="Address" />
          <v-text-field v-model="patient.dateOfBirth" label="Date of Birth" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNewPatient ? "Create" : "Save" }}
          </v-btn>
          <v-btn v-if="!isNewPatient" @click="deletePatient">Delete Patient</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "PatientDialog",
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNewPatient) {
        api.patients
            .create({
              name: this.patient.name,
              numericalCode: this.patient.numericalCode,
              identityCardNr: this.patient.identityCardNr,
              address: this.patient.address,
              dateOfBirth: this.patient.dateOfBirth,
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.patients
            .edit(this.patient.id,{
              id: this.patient.id,
              name: this.patient.name,
              numericalCode: this.patient.numericalCode,
              identityCardNr: this.patient.identityCardNr,
              address: this.patient.address,
              dateOfBirth: this.patient.dateOfBirth,
            })
            .then(() => this.$emit("refresh"));
      }
    },
    deletePatient(){
      api.patients.deleteById(this.patient.id)
          .then((response) => {
            if (response)
              this.$emit("refresh")
          });
    }
  },
  computed: {
    isNewPatient: function () {
      return !this.patient.id;
    },
  },
};
</script>

<style scoped></style>

<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{"Edit consultation" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="consultation.patientName" label="Patient's Name"  disabled/>
          <v-text-field v-model="consultation.date" label="Date"  disabled />
          <v-text-field v-model="consultation.details" label="Details" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "ConsultationDoctorDialog.vue",
  props: {
    consultation: Object,
    opened: Boolean,
    // doctorList: Array,
    // patientList: Array,
  },
  methods: {
    persist() {
        api.consultations
            .editDescription(this.consultation.id,
              this.consultation.details)
            .then(() => this.$emit("refresh"));
    },
  },

}

</script>

<style scoped>

</style>
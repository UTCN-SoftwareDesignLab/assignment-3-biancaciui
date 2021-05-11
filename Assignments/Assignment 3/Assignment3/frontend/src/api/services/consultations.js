import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allConsultations() {
        return HTTP.get(BASE_URL + "/consultations", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(consultation) {
        return HTTP.post(BASE_URL + "/consultations", consultation,{ headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    getConsultation(id) {
        return HTTP.get(BASE_URL + "/consultations/"+id, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    getByDoc(id) {
        return HTTP.get(BASE_URL + "/consultations/doc/"+id, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    getByPatient(id) {
        return HTTP.get(BASE_URL + "/consultations/patient/"+id, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    edit(id, consultation) {
        return HTTP.patch(BASE_URL + "/consultations/"+id, consultation, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    editDescription(id, details) {
        return HTTP.patch(BASE_URL + "/consultations/description/"+id, details, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    deleteById(id){
        return HTTP.delete(BASE_URL + "/consultations/"+ id , {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },

    deleteAll(){
        return HTTP.delete(BASE_URL + "/consultations" , {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },
};

import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allPatients() {
        return HTTP.get(BASE_URL + "/patients", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(patient) {
        return HTTP.post(BASE_URL + "/patients", patient,{ headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    getPatient(id) {
        return HTTP.get(BASE_URL + "/patients/"+id, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    edit(id, patient) {
        return HTTP.put(BASE_URL + "/patients/"+id, patient, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },

    deleteById(id){
        return HTTP.delete(BASE_URL + "/patients/"+ id , {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },

    deleteAll(){
        return HTTP.delete(BASE_URL + "/patients" , {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },
};

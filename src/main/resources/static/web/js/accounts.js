Vue.createApp({
    data() {
        return {
            clientData: {},
            clientInfo: {},
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientData = response.data;
                    console.log("this.clientData.accounts: ", this.clientData.accounts);
                    this.clientInfo = this.clientData.accounts.filter(account => account.isActive == true);
                    console.log("this.clientInfo: ", this.clientInfo.accounts);
                })
                .catch((error) => {
                    // handle error
                    this.errorMsg = "Error getting data accounts.js";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        create: function () {
            axios.post('/api/clients/current/accounts')
                .then(response => window.location.reload())
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                })
        },

        deleteAccount(accountNumber) {
             
            let config = {
             headers: { 'content-type': 'application/x-www-form-urlencoded'}
            }
            axios.patch(`/api/clients/current/accounts?number=${accountNumber}`, config)
            .then(response => window.location.href = "/web/accounts.html")
            .catch((error) => {
                this.errorMsg = error.response.data;
                this.errorToats.show();
            }) 
        },


    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')
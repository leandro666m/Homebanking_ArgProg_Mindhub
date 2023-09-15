Vue.createApp({
    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.creditCards = this.clientInfo.cards.filter(card => card.type == "CREDIT" && card.isActive == true);
                    this.debitCards = this.clientInfo.cards.filter(card => card.type == "DEBIT" && card.isActive == true);
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data en cards.js /api/clients/current";
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
        deleteCard(cardNumber) {
             
                let config = {
                 headers: { 'content-type': 'application/x-www-form-urlencoded'}
                }
                axios.patch(`/api/clients/current/cards?number=${cardNumber}`, config)
                .then(response => window.location.href = "/web/cards.html")
                .catch((error) => {
                    this.errorMsg = error.response.data;
                    this.errorToats.show();
                }) 
        },

        isCardExpired(thruDate) {
            const currentDate = new Date();
            const cardExpirationDate = new Date(thruDate);
            return currentDate > cardExpirationDate;
        },
        
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app')
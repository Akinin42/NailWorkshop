var customersApi = Vue.resource('/NailWorkshop/customers{/id}');

Vue.component('customer-form', {
    props: ['customers'],
    data: function() {
        return{
            name: '',
            phone: ''
        }
    },
    template: '<div>' +
                '<input type="text" placeholder="Input your name" v-model="name" />' +                
                '<input type="tel" placeholder="Input your phone" v-model="phone" />' +                
                '<input type="button" value="Save" @click="save" />' +
          '</div>',
    methods: {
        save: function() {
            var customer = {name: this.name, phone: this.phone};
            customersApi.save({}, customer).then(result => result.json().then(data => {
                this.customers.push(data);
            })
            )
        }
    }      
});
Vue.component('customer-row', {
  props: ['customer'],
  template: '<div>'+
                '<i>{{ customer.id }}</i> {{ customer.name }} {{ customer.phone }}'+
                '<span>' +
                    '<input type="button" value="Edit" @click="edit" />' +
                '</span>' +
                '</div>',
    methods: {
        edit: function() {
            
        }
    } 
});

Vue.component('customers-list', {
  props: ['customers'],
  template: '<div>' +
                '<customer-form :customers="customers" />'+
                '<customer-row v-for="customer in customers" :key="customer.id" :customer="customer" />'+
             '</div>',
  created: function() {
    customersApi.get().then(result => result.json().then(data => data.forEach(customer => this.customers.push(customer))))          
  }
  
});

var app = new Vue({
  el: '#customers',
  template: '<customers-list :customers="customers" />',
  data: {
    customers: []
  }
});
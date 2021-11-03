var customersApi = Vue.resource('/NailWorkshop/customers{/id}');

Vue.component('customer-row', {
  props: ['customer'],
  template: '<li><i>{{ customer.id }}</i> {{ customer.name }} {{ customer.phone }}</li>'
});

Vue.component('customers-list', {
  props: ['customers'],
  template: '<div><customer-row v-for="customer in customers" :key="customer.id" :customer="customer" /></div>',
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
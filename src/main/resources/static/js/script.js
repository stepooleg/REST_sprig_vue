function getIndex(list, id) {
    for (var i = 0; i<list.length; i++){
        if(list[i].id === id) {
            return i;
        }
    }

    return -1;
}

var messageApi = Vue.resource('/message{/id}')
Vue.component('messages-form', {
    props: ['messages', 'messageAttr'],
    data: function(){
        return{
            text:'',
            id: ''
        }
    },
    watch: {
        messageAttr: function(newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    methods:{
        save: function(){
            var message = { text: this.text };

            if(this.id){
            messageApi.update({id: this.id}, message).then(result =>
                result.json().then(data => {
                       var index = getIndex(this.messages, data.id);
                       this.messages.splice(index, 1, data);
                       this.text = '';
                       this.id = '';
                })
                )
            }else{
                messageApi.save({}, message).then(result =>
                                result.json().then(data => {
                                    this.messages.push(data);
                                    this.text = '';
                                })
                )
            }


        }
    },
    template:
    '<div>'+
        '<input type = "text" placeholder = "Введите сообщение" v-model = "text"/>'+
        '<input type = "button" value="Сохранить" @click="save"/>'+
    '</div>'
});
Vue.component('messages-row', {
    props: ['message', 'editMethod','messages'],
    methods: {
        edit: function(){
            this.editMethod(this.message);
        },
        del: function(){
                    messageApi.remove({id: this.message.id}).then(result =>{
                        if(result.ok){
                            this.messages.splice(this.messages.indexOf(this.message), 1)
                        }
                    })
        }
    },
    template:
    '<div>'+
     '<i> {{ message.id }}</i> {{ message.text }}'+
      '<span style="position: absolute; right: 0;">'+
            '<input type="button" value="Редактировать" @click="edit" />'+
            '<input type="button" value="X" @click="del" />'+
      '</span>'+
      '</div>'
});
Vue.component('messages-list', {
     data: function(){
                return{
                    message: null
                }
            },
     methods: {
            editMethod: function(message){
                this.message = message;
            }
        },
    props: ['messages'],
    template: '<div style="position: relative; width: 300px;">'+
    '<messages-form :messages="messages" :messageAttr="message">'+
    '</messages-form>'+
    '<messages-row v-for = "message in messages" '+
    ':key="message.id" '+
    ':message="message" '+
    ':messages="messages" '+
    ':editMethod="editMethod">'+
    '</messages-row></div>',

});

var app = new Vue({
    el: '#app',
    template:
    'div>'+
        '<div> v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>'+
        '<div v-else>'+
            '<div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>'+
            '<messages-list v-else:messages="messages"/>' +
        '</div>'+
    '</div>'
    data: {
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    created: function() {
//            messageApi.get().then(result =>
//                result.json().then(data =>
//                data.forEach(message => this.messages.push(message))
//                )
//            )
        }
})
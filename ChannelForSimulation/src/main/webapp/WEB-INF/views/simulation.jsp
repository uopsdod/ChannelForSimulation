<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<html lang="en">
<head>

<!-- add space vertically -->
<style>
.spacer5 { height: 5px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer10 { height: 10px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer15 { height: 15px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer20 { height: 20px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer25 { height: 25px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer30 { height: 30px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer35 { height: 35px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer40 { height: 40px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer45 { height: 45px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer50 { height: 50px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer100 { height: 100px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer200 { height: 200px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
</style>

<!-- 
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
<script src="https://unpkg.com/vue"></script>
<script
  src="https://code.jquery.com/jquery-3.2.1.min.js"
  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
  crossorigin="anonymous"></script>
</head>
<body>
	<div id="root">
		<h1>Simulation board</h1>
		
		<h3>General: </h3>
		tenantID: <input type="text" v-model="tenantID">
		<div class="spacer10"></div>
		typeID: <input type="text" v-model="typeID">
		<div class="spacer10"></div>
		<h3>Agent: </h3>
		<!-- 顯示資訊 -->
		Agent ID: <input type="text" v-model="userID_agent">
		<div class="spacer10"></div>
		Agent DialNo: <input type="text" v-model="dialNO_agent">
		<div class="spacer10"></div>
		Agent Name: <input type="text" v-model="userName_agent">
		<div class="spacer10"></div>
		<!-- 請求動作 -->
		<action action_name="updatestatus_ready" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<action action_name="updatestatus_notready" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<action action_name="accept_event" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<action action_name="agent_leaveroom" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<action action_name="agent_rejectevent" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<hr>
		<h3>Client: </h3>
		
		<!-- 顯示資訊 -->
		Client ID: <input type="text" v-model="userID_client">
		<div class="spacer10"></div>
		Client callID: <input type="text" v-model="callID_client">
		<div class="spacer10"></div>
		Client Name: <input type="text" v-model="userName_client">
		<div class="spacer10"></div>
		<!-- 請求動作 -->		
		<action action_name="rsp_senduserdata" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<action action_name="client_login" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		<action action_name="client_exit" v-bind:my_parent="rootObj"></action>
		<div class="spacer10"></div>
		
	</div> <!-- end of "root" div -->
</body>


<!-- JS -->
<script>
	var url_g;
	console.log("server_hostname: " + "${server_hostname}");
	if ('${server_hostname}' != 'localhost'){
		url_g = "http://${server_hostname}:${server_port}/${server_project}/triggerAction";
	}else{
		url_g = "http://${server_hostname}:${server_port}/triggerAction";
	}
	console.log("url_g: " + url_g);
</script>

<!-- Vue -->
<script>
	Vue.component('action',{
		template:
			'<button @click="sendReq">{{ this.action_name }}</button>',
		props: {
			action_name : { required: true },
			my_parent: {}
			
		},
		mounted:function(){
			console.log("this.my_parent.userID_agent: " + this.my_parent.userID_agent); // debug
		},
		methods: {
			sendReq: function(){
				
				var url = url_g;
				var querryData = {
									actionName : this.action_name
									,tenantID : this.my_parent.tenantID	
									,typeID : this.my_parent.typeID	
									,userID_agent : this.my_parent.userID_agent	
									,dialNO_agent : this.my_parent.dialNO_agent	
									,userName_agent : this.my_parent.userName_agent	
									,userID_client : this.my_parent.userID_client	
									,callID_client : this.my_parent.callID_client	
									,userName_client : this.my_parent.userName_client	
								};
// 				var promise = $.post(url, querryData, function() {}, "json");
				var promise = $.post(url, querryData);
        	}
		}
	});

    var app = new Vue({
        el: '#root',
        data: function(){
        	return {
        		tenantID : '${tenantID}',
        		typeID : '${typeID}',
        		userID_agent : '${userID_agent}',
        		dialNO_agent : '${dialNO_agent}',
        		userName_agent : '${userName_agent}',
        		userID_client : '${userID_client}',
        		callID_client : '${callID_client}',
        		userName_client : '${userName_client}',
        		rootObj:this
//         		isCouponVerified : false,
//         		coupon_code : ''
        	}
        },
        methods: {

//         	onCouponVerified: function(aCouponCode){
//         		console.log("coupon was verified!");
//         		console.log("aCouponCode: ", aCouponCode);
//         		this.coupon_code = aCouponCode;
//         		this.isCouponVerified = true;
//         	}
        }
    });
  </script>
</html>

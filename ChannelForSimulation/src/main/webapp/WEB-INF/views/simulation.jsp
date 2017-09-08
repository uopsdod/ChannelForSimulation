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
		<h3>Agent: </h3>
		<action action_name="updatestatus_ready"></action>
		<div class="spacer10"></div>
		<action action_name="updatestatus_notready"></action>
		<div class="spacer10"></div>
		<action action_name="accept_event"></action>
		<div class="spacer10"></div>
		<hr>
		<h3>Client: </h3>
		<action action_name="rsp_senduserdata"></action>
		<div class="spacer10"></div>
		<action action_name="client_login"></action>
		<div class="spacer10"></div>
		<action action_name="client_exit"></action>
		<div class="spacer10"></div>
		
	</div> <!-- end of "root" div -->
</body>


<!-- JS -->
<script>
	var url_g = "http://localhost:9009/triggerAction";
</script>

<!-- Vue -->
<script>
	Vue.component('action',{
		template:
			'<button @click="sendReq">{{ this.action_name }}</button>',
		props: {
			action_name : { required: true }
		},
		methods: {
			sendReq: function(){
				var url = url_g;
				var querryData = {actionName : this.action_name};
// 				var promise = $.post(url, querryData, function() {}, "json");
				var promise = $.post(url, querryData);
        	}
		}
	});

    var app = new Vue({
        el: '#root',
        data: function(){
        	return {
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

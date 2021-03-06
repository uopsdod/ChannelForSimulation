<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<!-- bootstrap custom css -->
<style type="text/css">
.center {
	margin: auto;
	text-align: center;
}

.left {
	margin: auto;
	text-align: left;
}


.nopadding {
   padding: 0 !important;
   margin: 0 !important;
}

.nopadding-horizontal{
    padding-right: 0 !important;
    padding-left: 0 !important;	
}

</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<link href="css/info360.css" rel="stylesheet">
<!-- <link href="resources/css/info360.css" rel="stylesheet"> -->

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

<link href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.5.3/css/bulma.css" rel="stylesheet">

<script src="https://unpkg.com/vue"></script>

<script
  src="https://code.jquery.com/jquery-3.2.1.min.js"
  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
  crossorigin="anonymous"></script>
</head>
<body>
	<div class="form-group col-sm-12">
		<div id="root">
			<div class="form-group col-sm-12 box center">
				<h1 class="title">RESTFUL 請求模擬 </h1>
			</div>
			<div class="form-group col-sm-12 box">
				<!-- 請求動作 -->
				<div class="form-group col-sm-12">
					<action action_name="tblInteraction_agentToAgent" v-bind:my_parent="rootObj">tblInteraction_agentToAgent</action>
<!-- 						<action action_name="tblinteraction_calllogtag" v-bind:my_parent="rootObj">準備就緒</action> -->
<!-- 						<action action_name="updatestatus_notready" v-bind:my_parent="rootObj">離席</action> -->
					<div class="spacer10"></div>
					<action action_name="accept_event" v-bind:my_parent="rootObj">接受客戶請求</action>
					<action action_name="agent_rejectevent" v-bind:my_parent="rootObj">拒絕客戶請求</action>
					<div class="spacer10"></div>
					<action action_name="agent_leaveroom" v-bind:my_parent="rootObj">離開一般通話</action>
					<div class="spacer10"></div>
					<action action_name="dialin" v-bind:my_parent="rootObj">撥打外線分機</action>
					<div class="spacer10"></div>
					<action action_name="dialinrsp" v-bind:my_parent="rootObj">接受外線分機</action>
					<div class="spacer10"></div>
					<action action_name="agent_leavedialinroom" v-bind:my_parent="rootObj">離開外線分機通話</action>
					<div class="spacer10"></div>
				</div>
			</div>
					
			<div class="form-group col-sm-12 box">
				<h4 class="title is-4">客戶</h4>
				<div class="form-group col-sm-6 nopadding">
<!-- 					<div class="form-group col-sm-12 nopadding-horizontal"> -->
<!-- 						<label for="typeID" class="col-sm-2"> 客戶編號: </label> -->
<!-- 						<div class="col-sm-8"> -->
<!-- 							<input type="text" class="input" id="typeID" v-model="userID_client"> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="form-group col-sm-12 nopadding-horizontal"> -->
<!-- 						<label for="typeID" class="col-sm-2"> 進線編號: </label> -->
<!-- 						<div class="col-sm-8"> -->
<!-- 							<input type="text" class="input" id="typeID" v-model="callID_client"> -->
<!-- 						</div> -->
<!-- 					</div>		 -->
					<div class="form-group col-sm-12 nopadding-horizontal">
						<label for="typeID" class="col-sm-2"> 進線資訊: </label>
						<div class="col-sm-8">
							<input type="text" class="input" id="typeID" v-model="userName_client">
						</div>
					</div>
					<div class="form-group col-sm-12 nopadding-horizontal">
						<label for="typeID" class="col-sm-2"> 小組編號: </label>
						<div class="col-sm-8">
							<input type="text" class="input" id="pilotID_client" v-model="pilotID_client">
						</div>
					</div>
				</div>
				
				<div class="form-group col-sm-1">
					<div style="border-left:1px solid #aaa;height:200px"></div>
				</div>
				
				<div class="form-group col-sm-4">
					<!-- 請求動作 -->		
					<div class="form-group col-sm-12">
						<action action_name="client_login" v-bind:my_parent="rootObj">客戶登入</action>
						<div class="spacer10"></div>
						<action action_name="rsp_senduserdata" v-bind:my_parent="rootObj">將客戶資訊寄給客服人員</action>
						<div class="spacer10"></div>
						<table>
						  <tr>
						    <td rowspan="2">
								<div class="spacer15"></div>
						    	<action action_name="setinteraction_client_exit" v-bind:my_parent="rootObj">電話結束</action>
						    </td>
						    <td>
						    	<action action_name="setinteraction" v-bind:my_parent="rootObj">更新房間資訊</action>
						    </td>
						  </tr>
						  <tr>
						    <td>
						    	<action action_name="client_exit" v-bind:my_parent="rootObj">客戶登出</action>
						    </td>
						  </tr>
						</table>
						
						
<!-- 						<div class="form-group col-sm-6"> -->
<!-- 							<action action_name="setinteraction" v-bind:my_parent="rootObj">電話結束&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</action> -->
<!-- 							<div class="spacer10"></div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group col-sm-6"> -->
<!-- 							<action action_name="setinteraction" v-bind:my_parent="rootObj">更新房間資訊</action> -->
<!-- 							<div class="spacer10"></div> -->
<!-- 							<action action_name="client_exit" v-bind:my_parent="rootObj">客戶登出</action> -->
<!-- 							<div class="spacer10"></div> -->
<!-- 						</div> -->
					</div>
				</div>
			</div>			

			
		</div> <!-- end of "root" div -->
	</div>
</body>


<!-- JS -->
<script>
	var url_g = "${RESTful_protocol}://${RESTful_hostname}:${RESTful_port}/${RESTful_project}/RESTful/";
	console.log("url_g: " + url_g);
	
	var sql = JSON.parse('{}');
	sql.tblInteraction_agentToAgent = '{"fromUserID":"301", "toUserID":"308", "msg":"hey yo", "date":"2017-09-30 18:00:12.000", "entityTypeID":"1", "tenantID":"3"}';
// 	'{"type":"message","userID":103,"text":"yo","sendto":"101","channel":"chat"}';
	
</script>

<!-- Vue -->
<script>
	Vue.component('action',{
		template:
// 			'<button @click="sendReq"><slot></slot></button>',
			'<div>'
				+'<slot></slot>'
				+ '<button @click="query" type="button" class="btn info360-btn-default" data-dismiss="modal"> QUERY </button>'
				+ '<button @click="insert" type="button" class="btn info360-btn-default" data-dismiss="modal"> INSERT </button>'
				+ '<button @click="update" type="button" class="btn info360-btn-default" data-dismiss="modal"> UPDATE </button>'
				+ '<button @click="deleteBean" type="button" class="btn info360-btn-default" data-dismiss="modal"> DELETE </button>'
				+ 'REQ: <input type="text" class="input" v-bind:id="formParams" v-model="formParams">'
				+ 'RSP: <input type="text" class="input" v-bind:id="response" v-model="response">'
			+ '</div>'
		,props: {
			action_name : { required: true },
			my_parent: {}
		}
	    ,data: function(){
	    	return {
	    		formParams : '{"dbid":"1"}'
	    		,response: '{}'
	    		
	//     		,coupon_code : ''
	    	}
	    }	
		,mounted:function(){
			console.log("this.my_parent.userID_agent: " + this.my_parent.userID_agent); // debug
			this.formParams = sql[this.action_name];
		}
		,methods: {
			query: function(){
				console.log("query");
				this.sendReq("getResource");
			},
			insert: function(){
				console.log("insert");
				this.sendReq("insertResource");
			},
			update: function(){
				console.log("update");
				this.sendReq("updateResource");
			},
			deleteBean: function(){
				console.log("deleteBean");
				this.sendReq("deleteResource");
			},
			sendReq: function(action){
				var url = url_g + action + "/" + this.action_name;
				console.log("url: " , url);
				console.log("formParams: " , this.formParams);
				console.log("sql[this.action_name]: " , sql[this.action_name]);
				// 先到這邊
				var promise = $.post(url, JSON.parse(this.formParams), function(data) {
					console.log("success! " , data);
					this.response = "" + JSON.stringify(data);
				}.bind(this), "json");
// 				var promise = $.post(url, this.formParams);

				// Assign handlers immediately after making the request,
				// and remember the jqxhr object for this request
// 				var jqxhr = $.post( "example.php", function() {
// 				  alert( "success" );
// 				})
				promise.done(function(data) {
					console.log( "second success: ", data );
				})
				promise.fail(function(data) {
					console.log( "error: ", data );
				})
				promise.always(function(data) {
					console.log( "finished: ", data );
				});
				 
				// Perform other work here ...
				 
				// Set another completion function for the request above
// 				jqxhr.always(function() {
// 				  alert( "second finished" );
// 				});
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
        		pilotID_client : '${pilotID_client}',
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

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
<body onbeforeunload="return cleanup()">
	<div class="spacer10"></div>
	<div class="form-group col-sm-12">
		<div id="watchdog_root">
			<div class="form-group col-sm-12 box center">
				<h1 class="title">Info360 系統監控</h1>
			</div>
			<br/>
			<watchdogbody></watchdogbody>
		</div> <!-- end of "root" div -->
	</div>
</body>

<script>

Vue.component('watchdogbody',{   
	template:` 
	<div>
		<table id="watchdogbodyTable" class="table table-striped table-bordered table-hover no-footer dataTable" aria-describedby="transferVoiceTable_info" style="width: 100%;">
			<thead>
			  	<tr>
					<th style="width: 25%;"> 系統名稱 </th>
					<th style="width: 25%;"> 系統狀態 </th>
				</tr>
			</thead>
			<tbody>
			   <tr v-for="system in systemList">
			      <td>
			      	<div >
				      	{{system.name}}
			      	</div>
			         
			      </td>
			      <td>
			      	<span v-if='system.status == "DOWN"' style="color: #d64262">{{system.status}}</span>
			      	<span v-if='system.status != "DOWN"' >{{system.status}}</span>
			      </td>
			   </tr>
			</tbody>
		</table>
	</div> 
	`, // end of template
	props: ['initParam01'],
	data(){
	  	return {
// 	  		systemList : JSON.parse('[{"name":"RabbitMQ","status":"UP"},{"name":"DB","status":"DOWN"}]')
	  		systemList : JSON.parse('[]')
	  	}
	},
	methods:{
		update(obj){
			console.log('update obj: ' , obj);
			var tmpSystemList = [];
			for (var index in obj.systemList) {
			    console.log(index + " -> " , obj.systemList[index]);
			    tmpSystemList.push(obj.systemList[index]);
			}
			this.systemList = tmpSystemList;
		}
	},
    created(){
		Event.$on('systemlistrefreshed',this.update);
    },
    mounted() { // 測試區
// 	   	let outputtest = JSON.parse('{ "systemList" : [{"name":"RabbitMQ","status":"UP"},{"name":"DB","status":"DOWN"},{"name":"VOICE","status":"UP"}] }')
// 	   	Event.$emit('systemlistrefreshed', outputtest); // 更新list方式
    }
})

// Vue component 要放前面 
window.Event = new Vue(); // for event trigger

var watchdog_root = new Vue({
    el: '#watchdog_root'
});

</script>

<!-- JS -->
<script>
var watchdog_obj_g = {
	systemList : JSON.parse('{}')
}

var updateStatus_interval_g;

$(document).ready(function () {
    console.log("doc ready");
    
    init();
    
    updateStatus_interval_g = setInterval(function(){
    	
	    // backend
		getRabbitMQ_status();
	
		// tblSystemMonitor 
		getTblSystemMonitor();
		
		console.log("watchdog_obj_g.systemList: " , watchdog_obj_g.systemList);
		
		// 更新
		Event.$emit('systemlistrefreshed', watchdog_obj_g); // 更新list方式
    }, 2000);
    
}); // end of $(document).ready(function ()
		
function init(){
    // backend
	getRabbitMQ_status();

	// tblSystemMonitor 
	getTblSystemMonitor();
	
	console.log("watchdog_obj_g.systemList: " , watchdog_obj_g.systemList);
	
	// 更新
	Event.$emit('systemlistrefreshed', watchdog_obj_g); // 更新list方式	
}		

function cleanup(){
	// 停掉更新狀態機制
	clearInterval(updateStatus_interval_g);
}

function getRabbitMQ_status(){
	
    var url_backend = "http://localhost:8080/BackendService/health";
//  var url_backend = "http://localhost:8002/health"; // testing
	$.get( url_backend , function( data ) {
		console.log("url_backend: " , data);
	})
	.always(function(data) {
//		console.log("always url_backend: " , data);
		// rabbutMQ server
		console.log("always url_backend.responseJSON: " , data.responseJSON);
		$.each( data.responseJSON, function( key, val ) {
//			console.log( key , ": " , val );
			var tmpName = "RabbeMQ Server";
			if ("rabbit" == key){
				var system = {
				    name : tmpName
				    ,status  : val.status
				};
// 				watchdog_obj_g.systemList.push(system); // 重要
				watchdog_obj_g.systemList[tmpName] = system;// 重要
			}
		});
		
		// rabbitMQ queues
		console.log("always url_backend.responseJSON.rabbitQueueCheck: " , data.responseJSON.rabbitQueueCheck);
		$.each( data.responseJSON.rabbitQueueCheck, function( key, val ) {
//			console.log( key , ": " , val );
			if ("status" != key){
				var system = {
				    name : key
				    ,status  : val.status
				};
// 				watchdog_obj_g.systemList.push(system); // 重要
				watchdog_obj_g.systemList[key] = system;// 重要
			}
		});		
		
		
	});	
}

function getTblSystemMonitor(){
// 	var url_tblSystemMonitor = "http://localhost:8082/Info360WebAPI/RESTful/getResource/tblSystemMonitor";
	var url_tblSystemMonitor = "http://localhost:8080/Info360WebAPI/RESTful/getResource/tblSystemMonitor";
	
	$.post( url_tblSystemMonitor , function( data ) {
//		console.log("url_tblSystemMonitor: " , data);
		console.log("always url_tblSystemMonitor: " , data);
		$.each( data, function( key, val ) {
			console.log( key , ": " , val );
			var now_ts = new Date().getTime();
			var ts = val.timestamp;
			var timeDiff = Math.abs(now_ts - ts);
			var diffSec = Math.ceil(timeDiff / (1000));
			console.log("diffSec: " , diffSec);
			var status = "DOWN";
			if (diffSec <= 10) status = "UP";
			
			var system = {
				    name : val.name
				    ,status  : status
				};
// 			watchdog_obj_g.systemList.push(system); // 重要
			watchdog_obj_g.systemList[val.name] = system;// 重要
		});
	})	
}




</script>
</html>

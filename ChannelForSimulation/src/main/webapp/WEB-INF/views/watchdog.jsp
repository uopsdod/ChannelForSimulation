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
					<th style="width: 25%;"> 連線人數 </th>
				</tr>
			</thead>
			<tbody>
				<!-- 若沒有抓過任何一次資訊 -->
			   <tr v-if="getSystemListLen == 0">
				  <td colspan="3" style="color: #d64262"><span>BACKEND Server offline</span></td>
			   </tr>
			   <tr v-if="getSystemListLen == 0">
				  <td colspan="3" style="color: #d64262"><span>RESTFUL Server offline</span></td>
			   </tr>
			   <!-- 若抓過任資訊  - backend -->
			   <tr v-if="system.from == 'backend' && !getTimeGap(ts_backend)" v-for="(system, index) in systemList">
				  <td v-if="index == 0" colspan="3" style="color: #d64262"><span>BACKEND Server offline</span></td>
			   </tr>
			   <tr v-if="system.from == 'backend' && getTimeGap(ts_backend)" v-for="system in systemList">
			      <td>
				      {{system.name}}
			      </td>
			      <td>
			      	<span v-if='system.status == "DOWN"' style="color: #d64262">{{system.status}}</span>
			      	<span v-if='system.status != "DOWN"' >{{system.status}}</span>
			      </td>
			      <td>
			      	<span v-if='system.q_count > 1' style="color: #d64262">{{system.q_count}}</span>
			      	<span v-if='system.q_count <= 1' >{{system.q_count}}</span>
			      </td>
			   </tr>
			   <!-- 若抓過任資訊  - restful -->
			   <tr v-if="system.from == 'restful' && !getTimeGap(ts_rest)" v-for="(system, index) in systemList">
			   		<td v-if="index == 0" colspan="3" style="color: #d64262"><span>RESTFUL Server offline</span></td>
			   </tr>
			   <tr v-if="system.from == 'restful' && getTimeGap(ts_rest)" v-for="system in systemList">
			      <td>
				      {{system.name}}
			      </td>
			      <td>
			      	<span v-if='system.status == "DOWN"' style="color: #d64262">{{system.status}}</span>
			      	<span v-if='system.status != "DOWN"' >{{system.status}}</span>
			      </td>
			      <td>
			      </td>
			   </tr>
			</tbody>
		</table>
	
		<!-- 參數檔 -->
		<table id="watchdogbodyTable_config" class="table table-striped table-bordered table-hover no-footer dataTable" aria-describedby="watchdogbodyTable_config_info" style="width: 100%;">
			<thead>
			  	<tr>
					<th style="width: 18%;"> 系統名稱 </th>
					<th style="width: 14%;"> 參數一 </th>
					<th style="width: 17%;"> 參數二 </th>
					<th style="width: 17%;"> 參數三 </th>
					<th style="width: 17%;"> 參數四 </th>
					<th style="width: 17%;"> 更新時間 </th>
				</tr>
			</thead>		
				<tr v-if="!getTimeGap(ts_rest)">
					<td colspan="6" style="color: #d64262"><span>RESTFUL Server offline</span></td>
				</tr>
				<tr v-if="getTimeGap(ts_rest)" v-for="config in configList">
				   <td>
				    {{config.name}}
				   </td>
				   <td><span >{{config.param01}}</span></td>
				   <td><span >{{config.param02}}</span></td>
				   <td><span >{{config.param03}}</span></td>
				   <td><span >{{config.param04}}</span></td>
				   <td><span >{{config.timegap}}</span></td>
				</tr>
	  	 </table>
	  	 
	  	<!-- 版本 -->
		<table id="watchdogbodyTable_version" class="table table-striped table-bordered table-hover no-footer dataTable" aria-describedby="watchdogbodyTable_config_info" style="width: 100%;">
			<thead>
			  	<tr>
					<th style="width: 18%;"> 系統名稱 </th>
					<th style="width: 14%;"> 版本號 </th>
				</tr>
			</thead>		
		   <tr v-for="system in versionList">
		      <td>
			      {{system.name}}
		      </td>
		      <td>
			      {{system.param01}}
		      </td>
		   </tr>
 	 	 </table>		
	  	
	</div> 
	`, // end of template
	props: ['initParam01'],
	data(){
	  	return {
// 	  		systemList : JSON.parse('[{"name":"RabbitMQ","status":"UP"},{"name":"DB","status":"DOWN"}]')
	  		systemList : JSON.parse('[]')
	  		,configList : JSON.parse('[]')
	  		,versionList : JSON.parse('[]')
	  		,ts_rest : 0
	  		,is_alive_rest : false
	  		,ts_backend : 0
	  		,is_alive__backend : false
	  	}
	},
	methods:{
		update(obj){ 
			console.log('update obj: ' , obj);
			// system
			var tmpSystemList = [];
			for (var index in obj.systemList) {
			    console.log(index + " -> " , obj.systemList[index]);
			    tmpSystemList.push(obj.systemList[index]);
			}
			this.systemList = tmpSystemList;
			
			// config
			console.log('update obj.configList: ' , obj.configList);
			var tmpConfigList = [];
			for (var index in obj.configList) {
			    console.log(index + " -> " , obj.configList[index]);
			    tmpConfigList.push(obj.configList[index]);
			}
			
			// version
			console.log('update obj.versionList: ' , obj.versionList);
			var tmpVersionList = [];
			for (var index in obj.versionList) {
			    console.log(index + " -> " , obj.versionList[index]);
			    tmpVersionList.push(obj.versionList[index]);
			}
			this.versionList = tmpVersionList;
			
			// 以各專案來區分
			tmpConfigList.sort(function(a, b){
				 var nameA=a.name.toLowerCase(), nameB=b.name.toLowerCase();
				 if (nameA < nameB) //sort string ascending
				  return -1;
				 if (nameA > nameB)
				  return 1;
				 return 0; //default return value (no sorting)
				});
			this.configList = tmpConfigList;
			
			// ts
			this.ts_rest = obj.ts_rest;
			this.ts_backend = obj.ts_backend;
			
		}
		,getTimeGap(val){
	        var now_ts = new Date().getTime();
	        var timeDiff = Math.abs(now_ts - val);
			var diffSec = Math.ceil(timeDiff / (1000));
			console.log("getTimeGap diffSec: " + diffSec);
			if (diffSec > 5){
				return false;
			}else{
				return true;
			}
		}
	},
	computed : {
	    // a computed getter
	    getSystemListLen: function () {
	      // `this` points to the vm instance
	      console.log("this.systemList: " , this.systemList);
	      return this.systemList.length;
	    }		
	},
// 	watch: {
// 		ts_rest: function (val) {
// 			console.log("ts_rest val: " + val);
// 			this.ts_rest = val;
// 	        // `this` points to the vm instance
// 	        var now_ts = new Date().getTime();
// 	        var timeDiff = Math.abs(now_ts - val);
// 			var diffSec = Math.ceil(timeDiff / (1000));
// 			console.log("ts_rest diffSec: " + diffSec);
			
// 			if (diffSec > 5){
// 				this.is_alive_rest = false;
// 			}else{
// 				this.is_alive_rest = true;
// 			}
			
// 	    }		
// 	},
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
	,configList : JSON.parse('{}')
	,versionList : JSON.parse('{}')
	,ts_rest : 0
	,ts_backend : 0
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
		
		// config
		getConfig();
		
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
	
	// config
	getConfig();
	
	console.log("watchdog_obj_g.systemList: " , watchdog_obj_g.systemList);
	
	// 更新
	Event.$emit('systemlistrefreshed', watchdog_obj_g); // 更新list方式	
}		

function cleanup(){
	// 停掉更新狀態機制
	clearInterval(updateStatus_interval_g);
}

function getConfig(){
	var url_tblSystemMonitor_config = "${RESTful_protocol}://${RESTful_hostname}:${RESTful_port}/${RESTful_project}/RESTful/getResource/tblSystemMonitor_config";
	console.log("url_tblSystemMonitor_config: " , url_tblSystemMonitor_config);
	$.post( url_tblSystemMonitor_config , function( data ) {
//		console.log("url_tblSystemMonitor: " , data);
		console.log("always url_tblSystemMonitor_config: " , data);
		$.each( data, function( key, val ) {
// 			console.log( key , ": " , val );
			console.log( val.name + " ans: " , val.name.indexOf("version") );
			

			var now_ts = new Date().getTime();
			var ts = val.timestamp;
			var timeDiff = Math.abs(now_ts - ts);
			var diffSec = Math.ceil(timeDiff / (1000));
//	 		console.log("diffSec: " , diffSec);
			var diff = secondsToHms(diffSec);
//	 		console.log("diff: " , diff);
//	 		var status = "DOWN";
//	 		if (diffSec <= 10) status = "UP";
			var system = null;
				
			if (val.db_url){
				system = {
					    name : val.name
					    ,param01 : val.db_url
					    ,param02 : val.ip
					    ,param03 : val.db_username
					    ,param04 : val.db_password
					    ,timegap : diff
					};
			}else{
				system = {
					    name : val.name
					    ,param01 : val.protocol
					    ,param02 : val.ip
					    ,param03 : val.port
					    ,param04 : val.project
					    ,timegap : diff
					};
			}
			
			if (val.name.indexOf('version') != -1){
				console.log( "version_matched: " , val );
				watchdog_obj_g.versionList[val.name] = system;// 重要
			}else{
				watchdog_obj_g.configList[val.name] = system;// 重要				
			}

		}); // end of foreach 
		var now_ts = new Date().getTime();
		watchdog_obj_g.ts_rest = now_ts;
	})	
}

function getRabbitMQ_status(){
	getRabbitMQ_status_metrics();
}

function getRabbitMQ_status_metrics(){
//     var url_backend_metrics = "http://localhost:8080/BackendService/metrics";
    var url_backend_metrics = "${BACKEND_protocol}://${BACKEND_hostname}:${BACKEND_port}/${BACKEND_project}/metrics";
    console.log("url_backend_metrics: " , url_backend_metrics);
//  var url_backend = "http://localhost:8002/health"; // testing
	$.get( url_backend_metrics , function( data ) {
		console.log("url_backend_metrics success: " , data); // ajax get 方法一定會跑到 always, 而post則server不在的話，不會跑進always
		var now_ts = new Date().getTime();
		watchdog_obj_g.ts_backend = now_ts;
	})
	.always(function(data) {
		// rabbutMQ server
		console.log("always url_backend_metrics: " , data);
		console.log("always url_backend_metrics_test: " , data['rabbit.queue.CHANNEL_TO_BACKEND_QUEUE01.currentMessageCount']);
		
		getRabbitMQ_status_health(data);
		
		
	});		
}

function getRabbitMQ_status_health(metrics){
//     var url_backend_health = "http://localhost:8080/BackendService/health";
    var url_backend_health = "${BACKEND_protocol}://${BACKEND_hostname}:${BACKEND_port}/${BACKEND_project}/health";
    console.log("url_backend_health: " , url_backend_health);
//  var url_backend = "http://localhost:8002/health"; // testing
	$.get( url_backend_health , function( data ) {
// 		console.log("url_backend_health: " , data);
	})
	.always(function(data) {
		// rabbutMQ server
		console.log("always url_backend_health.responseJSON: " , data.responseJSON);
		$.each( data.responseJSON, function( key, val ) {
//			console.log( key , ": " , val );
			var tmpName = "RabbeMQ Server";
			if ("rabbit" == key){
				var system = {
				    name : tmpName
				    ,status  : val.status
				    ,from : "backend"
				};
// 				watchdog_obj_g.systemList.push(system); // 重要
				watchdog_obj_g.systemList[tmpName] = system;// 重要
			}
		});
		
		// rabbitMQ queues
		if (data.responseJSON) {
			console.log("always url_backend_health.responseJSON.rabbitQueueCheck: " , data.responseJSON.rabbitQueueCheck);
			$.each( data.responseJSON.rabbitQueueCheck, function( key, val ) {
	//			console.log( key , ": " , val );
				if ("status" != key){
					var system = {
					    name : key
					    ,status  : val.status
					    ,q_count : metrics['rabbit.queue.' + key + '.currentConsumerCount']
						,from : "backend"
					};
	// 				watchdog_obj_g.systemList.push(system); // 重要
					watchdog_obj_g.systemList[key] = system;// 重要
				}
			});
		}
	});		
}



function getTblSystemMonitor(){
// 	var url_tblSystemMonitor = "http://localhost:8082/Info360WebAPI/RESTful/getResource/tblSystemMonitor";
// 	var url_tblSystemMonitor = "http://localhost:8080/Info360WebAPI/RESTful/getResource/tblSystemMonitor";
	var url_tblSystemMonitor = "${RESTful_protocol}://${RESTful_hostname}:${RESTful_port}/${RESTful_project}/RESTful/getResource/tblSystemMonitor";
	console.log("url_tblSystemMonitor: " , url_tblSystemMonitor);
	
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
				    ,from : "restful"
				};
// 			watchdog_obj_g.systemList.push(system); // 重要
			watchdog_obj_g.systemList[val.name] = system;// 重要
		});
	})	
}

// convert secs
function secondsToHms(d) {
    d = Number(d);
    var h = Math.floor(d / 3600);
    var m = Math.floor(d % 3600 / 60);
    var s = Math.floor(d % 3600 % 60);

    var hDisplay = h > 0 ? h + (h == 1 ? " hr, " : " hrs, ") : "";
    var mDisplay = m > 0 ? m + (m == 1 ? " min, " : " mins, ") : "";
    var sDisplay = s > 0 ? s + (s == 1 ? " sec" : " secs") : "";
    return hDisplay + mDisplay + sDisplay; 
}


</script>
</html>

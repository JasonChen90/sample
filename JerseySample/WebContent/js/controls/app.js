(function (){
	var app = angular.module('blogApp',['ngRoute']);
	app.config(function ($routeProvider,$locationProvider,$provide){
		$locationProvider.html5Mode(false);
		$routeProvider
		.when('/articals',{
			templateUrl:'ArticalList.html',
			controller:'articalListCtrl'
		})
		.when('/edit/:articalId',{
			templateUrl:'EditArtical.html',
			controller:'editArticalCtrl'
		})
		.when('/artical/1',{
			templateUrl:'Artical.html',
			controller:'articalCtrl'
		})
		.otherwise({redirectTo:'/articals'});
	});
	app.controller("appCtrl",function ($scope,$http,$route, $routeParams, $location){
		$scope.init = function(){
		};
	});

}());
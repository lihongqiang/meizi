angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home',
		controllerAs: 'controller'
	}).when('/login', {
		templateUrl : 'signin.html',
		controller : 'navigation',
		controllerAs: 'controller'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

	//$httpProvider.default.headers.post = {
	//	'Content-Type': 'application/json'
	//}

}).controller('navigation',

		function($rootScope, $http, $location, $route) {
			
			var self = this;

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			var authenticate = function(credentials, callback) {

                var data = credentials ? {
                    "username": credentials.username,
                    "password": credentials.password
                } : {
					"username": "",
					"password": ""
				};

				//$http.post("http://162.105.16.229/vendor_login", data).success(function(response) {
				//	if (response.data.status == true) {
				//		if(response.data[code] == 200){
				//			$rootScope.authenticated = true;
				//		}else{
				//			$rootScope.authenticated = false;
				//		}
                //
				//	} else {
				//		$rootScope.authenticated = false;
				//	}
				//	callback && callback($rootScope.authenticated);
				//}, function() {
				//	$rootScope.authenticated = false;
				//	callback && callback(false);
				//});

				//$.post("http://162.105.16.229/vendor_login", data,
				//	function(data){
				//		alert(data.code); // John
				//		console.log(data.code); //  2pm
				//	}, "json");

				$http.get('authenticate', {
					params : data
				}).then(function(response) {
					if (response.data.status == true) {
						$rootScope.authenticated = true;
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback($rootScope.authenticated);
				}, function() {
					$rootScope.authenticated = false;
					callback && callback(false);
				});

			}

			authenticate();

			self.credentials = {};
			self.login = function() {
				authenticate(self.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						$location.path("/");
						flag = true;
						self.error = false;
						$rootScope.authenticated = true;
					} else {
                        alert("Username or password error, please try again.")
						console.log("Login failed")
						$location.path("/login");
						flag = false;
						self.error = true;
						$rootScope.authenticated = false;
					}
				})
			};

			self.logout = function() {
				$http.post('logout', {}).finally(function() {
					$rootScope.authenticated = false;
					$location.path("/");
				});
			}

		}).controller('home', function($http, $rootScope) {
			var self = this;
			$http.get('/resource/').then(function(response) {
				self.greeting = response.data;
			})
			$rootScope.upload = function(data){
				$http.post('/upload', data).finally(function(){

				});
			}
});

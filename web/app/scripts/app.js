'use strict';

/**
 * @ngdoc overview
 * @name fitrecipeApp
 * @description
 * # fitrecipeApp
 *
 * Main module of the application.
 */
angular
  .module('fitrecipeApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

  $(document).ready(function() {
    $('#fullpage').fullpage();
});

'use strict';

/**
 * @ngdoc function
 * @name fitrecipeApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the fitrecipeApp
 */
angular.module('fitrecipeApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

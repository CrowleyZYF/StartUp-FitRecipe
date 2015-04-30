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
    'fitRecipeRouter',    
    'fitRecipeControllers',
    'ngAnimate'
  ])
  .config(function () {
  });

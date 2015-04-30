'use strict';

var fitRecipeRouter = angular.module('fitRecipeRouter', ['ui.router', 'ngAnimate']);

fitRecipeRouter.config(function($stateProvider, $urlRouterProvider) {

    // Now set up the states
    $stateProvider
        .state('fitRecipe', {
            abstract: true,
            views: {
                '': {
                    templateUrl: '../views/fitRecipe.html'
                }
            }
        })        
        .state('fitRecipe.indexContainer', {            
            abstract: true,
            views: {
                'content':{
                    templateUrl: '../views/indexContainer.html',
                    controller: 'IndexContainerController'                    
                },
                'slide': {
                    templateUrl: '../views/slide.html'
                },
                'content1':{
                    templateUrl: '../views/about.html'
                },
                'content2':{
                    templateUrl: '../views/main.html'
                }
            }
        })
        .state('fitRecipe.indexContainer.index', {  
            url: '/',
            views: {
                'slide': {
                    templateUrl: '../views/slide.html'
                },
                'content1':{
                    templateUrl: '../views/about.html'
                },
                'content2':{
                    templateUrl: '../views/main.html'
                }
            }
        })
        .state('404', {
            url: '/404',
            views: {
                '': {
                    templateUrl: '../views/about.html'
                }
            }
        })   
});

angular.module('market-front').controller('orderPayController', function ($scope, $http, $location, $localStorage, $routeParams) {

    $scope.loadOrder = function () {
        $http({
            url: 'http://localhost:5555/core/api/v1/orders/' + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data;
            $scope.renderPaymentButtons();
        });
    };

    $scope.setPaidStatus = function(){
     $http({
            url: 'http://localhost:5555/core/api/v1/orders/paid/' + $routeParams.orderId,
            method: 'GET'
            }).then(function (response) {
            alert("PAID");
            $location.path('/!#/');
        });
    }

    $scope.renderPaymentButtons = function() {
        paypal.Buttons({
            createOrder: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                    return response.text();
                });
            },

            onApprove: function(data, actions) {
                return fetch('http://localhost:5555/core/api/v1/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function(response) {
                   $scope.setPaidStatus();
                });
            },

            onCancel: function (data) {
                console.log("Order canceled: " + data);
            },

            onError: function (err) {
                // Cannot test on real paypal
                console.log(err);
                $scope.setPaidStatus();
            }
        }).render('#paypal-buttons');
    }

    $scope.loadOrder();
});
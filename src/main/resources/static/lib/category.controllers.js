warehouseCategory.controller("CategoryListController", ['CategoryService', function ($resource, $scope){


    $scope.subject = '';
    $scope.code = '';
    $scope.pageSize = 10;

    $scope.submit = function (subject, code, pageSize){

        console.log("hi");

    }


}])//...

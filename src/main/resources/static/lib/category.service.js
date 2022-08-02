warehouseCategory.factory("CategoryService", ['$resource', function ($resource){

    let url = "http://localhost:8081/category/search";

    return
        $resource(url, {}, {
            'get':    {method: 'GET'},
            'save':   {method: 'POST'},
            'query':  {method: 'GET', isArray: true},
            'remove': {method: 'DELETE'},
            'delete': {method: 'DELETE'}
        }, {});

}])//...
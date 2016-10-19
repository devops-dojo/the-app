/// <reference path="../services/navigation.ts"/>

interface INavigationScope extends ng.IScope {
    vm: NavigationController;
}

class NavigationController {
    items: INavigationItem[] = [];

    static $inject = ['$scope', 'navigationService'];

    constructor(private $scope:INavigationScope, private navigationService:INavigationService) {
        navigationService.getNavigation().then((data: INavigationItem[]) => this.items = data);

        $scope.vm = this;
    }
}

eshop.controller('navigationController', NavigationController);

class DropdownController {
    static $inject = ['$scope'];

    constructor(private $scope) {
        $scope.vm = this;
    }

    toggled(open) {
        console.log('Dropdown is now: ', open);
    }
}

eshop.controller('dropdownController', DropdownController);

function getMenu(index) {
    if (index == '2-1'){
        return new Menu('2-1','添加账户','./ylcard.html');
    }else if (index == '2-2'){
        return new Menu('2-2','报销','./reimb.html');
    }else if (index == '2-3'){
         return new Menu('2-3','报销统计','./reimbTotal.html');
    }
    
}

function Menu(index,name,url) {
    this.index = index;
    this.name= name;
    this.url=url;
}
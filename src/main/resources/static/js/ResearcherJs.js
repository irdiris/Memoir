
// SIDEBAR DROPDOWN
const allDropdown = document.querySelectorAll('#sidebar .side-menu-dropdown');
const sidebar = document.getElementById('sidebar');

allDropdown.forEach(item => {
    const a = item.parentElement.querySelector('a:first-child');
    a.addEventListener('click', function (e) {
        e.preventDefault();

        if (!this.classList.contains('active')) {
            allDropdown.forEach(i => {
                const aLink = i.parentElement.querySelector('a:first-child');

                aLink.classList.remove('active');
                i.classList.remove('show');
            })
        }

        this.classList.toggle('active');
        item.classList.toggle('show');
    })
})

// SIDEBAR COLLAPSE
const toggleSidebar = document.querySelector('nav .toggle-sidebar');
const allSideDivider = document.querySelectorAll('#sidebar .divider');

if (sidebar.classList.contains('hide')) {
    allSideDivider.forEach(item => {
        item.textContent = '-'
    })
    allDropdown.forEach(item => {
        const a = item.parentElement.querySelector('a:first-child');
        a.classList.remove('active');
        item.classList.remove('show');
    })
} else {
    allSideDivider.forEach(item => {
        item.textContent = item.dataset.text;
    })
}

toggleSidebar.addEventListener('click', function () {
    sidebar.classList.toggle('hide');

    if (sidebar.classList.contains('hide')) {
        allSideDivider.forEach(item => {
            item.textContent = '-'
        })

        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
    } else {
        allSideDivider.forEach(item => {
            item.textContent = item.dataset.text;
        })
    }
})

// Handle clicking outside of the sidebar to hide it
document.addEventListener('click', function (event) {
    const sidebar = document.getElementById('sidebar');
    const toggleSidebar = document.querySelector('nav .toggle-sidebar');

    if (!sidebar.contains(event.target) && event.target !== toggleSidebar) {
        sidebar.classList.add('hide');
        allSideDivider.forEach(item => {
            item.textContent = '-'
        })
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
    }
})

sidebar.addEventListener('mouseleave', function () {
    if (this.classList.contains('hide')) {
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
        allSideDivider.forEach(item => {
            item.textContent = '-'
        })
    }
})

sidebar.addEventListener('mouseenter', function () {
    if (this.classList.contains('hide')) {
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        })
        allSideDivider.forEach(item => {
            item.textContent = item.dataset.text;
        })
    }
})




// PROFILE DROPDOWN
const profile = document.querySelector('nav .profile');
const imgProfile = profile.querySelector('img');
const dropdownProfile = profile.querySelector('.profile-link');

imgProfile.addEventListener('click', function () {
    dropdownProfile.classList.toggle('show');
})

//NOTIFICATION DROPDOWN
const notif = document.querySelector('nav .notif');
const icon= notif.querySelector('#bell');
const dropdownnotif= notif.querySelector('.notif-link');

icon.addEventListener('click', function () {
    dropdownnotif.classList.toggle('show');
})




// MENU
const allMenu = document.querySelectorAll('main .content-data .head .menu');

allMenu.forEach(item=> {
    const icon = item.querySelector('.icon');
    const menuLink = item.querySelector('.menu-link');

    icon.addEventListener('click', function () {
        menuLink.classList.toggle('show');
    })
})



window.addEventListener('click', function (e) {
    if(e.target !== imgProfile) {
        if(e.target !== dropdownProfile) {
            if(dropdownProfile.classList.contains('show')) {
                dropdownProfile.classList.remove('show');
            }
        }
    }


    window.addEventListener('click', function (e) {
        if(e.target !== icon) {
            if(e.target !== dropdownnotif) {
                if(dropdownnotif.classList.contains('show')) {
                    dropdownnotif.classList.remove('show');
                }
            }
        }



        allMenu.forEach(item=> {
            const icon = item.querySelector('.icon');
            const menuLink = item.querySelector('.menu-link');

            if(e.target !== icon) {
                if(e.target !== menuLink) {
                    if (menuLink.classList.contains('show')) {
                        menuLink.classList.remove('show')
                    }
                }
            }
        })
    })

})




function getHPCHistory() {
    const url = "/Researcher/HPCHistory";
    var token = localStorage.getItem("token")
    const headers = {

        'Authorization': token.valueOf()
    };

    fetch(url, {
        method: 'GET',
        headers: headers
    })
        .then(response => response.text())
        .then(data => {
            const tableContainer = document.getElementById('table-wrapper');
            tableContainer.innerHTML = data;

        })
        .catch(error => {
            // Handle any errors
        });
}
function getnotification() {
    const url = "/Researcher/Notification";
    var token = localStorage.getItem("token")
    const headers = {

        'Authorization': token.valueOf()
    };

    fetch(url, {
        method: 'GET',
        headers: headers
    })
        .then(response => response.text())
        .then(data => {
            const tableContainer = document.getElementById('notification');
            tableContainer.innerHTML = data;

        })
        .catch(error => {
            // Handle any errors
        });
}

function getSerialNumber(button) {
    var row = button.parentNode.parentNode;

    var rowId = row.id;
    localStorage.setItem("rowId", rowId);
}
function sendRequest(){
    var dateOfAcquisition = document.getElementById("dateOfTaking").value
    var dateOfReturn = document.getElementById("dateOfReturn").value
    var serialNumber = localStorage.getItem("rowId").valueOf()
    var formData = new FormData();
    formData.append("dateOfAcquisition", dateOfAcquisition);
    formData.append("dateOfReturn", dateOfReturn);
    formData.append("serialNumber", serialNumber);
    var token = localStorage.getItem("token");

    fetch("/Researcher/sendRequest", {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": token.valueOf(),
        },
    })

}
function sendRequestHPC(){
    var dateOfAcquisition = document.getElementById("date").value
    var serialNumber = localStorage.getItem("rowId").valueOf()
    var formData = new FormData();
    formData.append("dateOfAcquisition", dateOfAcquisition);
    formData.append("serialNumber", serialNumber);

    var token = localStorage.getItem("token");

    fetch("/Researcher/sendHPCRequest", {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": token.valueOf(),
        },
    })

}
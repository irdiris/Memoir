
//DARK MODE
let toggleBtn = document.getElementById('toggle-btn');

let darkMode = localStorage.getItem('dark-theme');

const enableDarkMode = () =>{
    let body = document.body;
    toggleBtn.classList.replace('fa-sun', 'fa-moon');
    body.classList.add('dark-theme');
    localStorage.setItem('dark-theme', 'enabled');
}

const disableDarkMode = () =>{
    let body = document.body;
    toggleBtn.classList.replace('fa-moon', 'fa-sun');
    body.classList.remove('dark-theme');
    localStorage.setItem('dark-theme', 'disabled');
}

if(darkMode === 'enabled'){
    enableDarkMode();
}
function toggleDarkMode() {

    darkMode = localStorage.getItem('dark-theme');
    console.log(darkMode);
    if (darkMode === 'disabled') {
        enableDarkMode();
    } else {
        disableDarkMode();
    }
}



// SIDEBAR COLLAPSE
const toggleSidebar = document.querySelector('nav .toggle-sidebar');
const allSideDivider = document.querySelectorAll('#sidebar .divider');



function toggleSide() {

    const allSideDivider = document.querySelectorAll('#sidebar .divider');
    sidebar.classList.toggle('hide');

    if (sidebar.classList.contains('hide')) {
        allSideDivider.forEach(item => {
            item.textContent = '-';
        });

        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        });
    } else {
        allSideDivider.forEach(item => {
            item.textContent = item.dataset.text;
        });
    }
}

function handleSidebarMouseLeave() {
    if (sidebar.classList.contains('hide')) {
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        });
        allSideDivider.forEach(item => {
            item.textContent = '-';
        });
    }
}



function handleSidebarMouseEnter() {
    if (sidebar.classList.contains('hide')) {
        allDropdown.forEach(item => {
            const a = item.parentElement.querySelector('a:first-child');
            a.classList.remove('active');
            item.classList.remove('show');
        });
        allSideDivider.forEach(item => {
            item.textContent = item.dataset.text;
        });
    }
}



// PROFILE DROPDOWN


function handleImgProfileClick() {
    const profile = document.querySelector('nav .profile');
    const imgProfile = profile.querySelector('img');
    const dropdownProfile = profile.querySelector('.profile-link');
    dropdownProfile.classList.toggle('show');
}

//NOTIFICATION DROPDOWN


function handleIconClick() {
    const notif = document.querySelector('nav .notif');
    const icon= notif.querySelector('#bell');
    const dropdownnotif= notif.querySelector('.notif-link');
    dropdownnotif.classList.toggle('show');
}






    function getHistory() {
    const url = "/View/Student/History";
    var token = localStorage.getItem("token")
    const headers = {

    'Authorization': token.valueOf()
};

    fetch(url, {
    method: 'GET',
    headers: headers
})
    .then(response => response.text())
    .then(htmlContent => {
    document.open();
    document.write(htmlContent);
    //	const buttonnew = document.getElementById('toggle-btn');
    //	buttonnew.addEventListener('click', toggleDarkMode);
    document.close();

})
    .catch(error => {
    // Handle any errors
});
}
function getAllocationItems() {
    const url = "/View/Student/Request";
    var token = localStorage.getItem("token")
    const headers = {

        'Authorization': token.valueOf()
    };

    fetch(url, {
        method: 'GET',
        headers: headers
    })
        .then(response => response.text())
        .then(htmlContent => {
            document.open();
            document.write(htmlContent);

            document.close();

        })
        .catch(error => {
            // Handle any errors
        });
}

function submitForm(form) {
    // Get the form data
    const formData = new FormData(form);
    var token = localStorage.getItem("token");
    // Submit the form
    fetch("/View/Student/updateProfile", {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": token.valueOf(),
        },
    })

}

const myForm = document.getElementById("updateForm");

myForm.addEventListener("submit", function(event) {
    event.preventDefault();
    submitForm(myForm);
});

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

    fetch("/View/Student/sendRequest", {
        method: "POST",
        body: formData,
        headers: {
            "Authorization": token.valueOf(),
        },
    })
}

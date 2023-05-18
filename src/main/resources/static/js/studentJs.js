
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
    console.log("wtf");
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


// APEXCHART
//CHART
var options = {
    series: [{
        name: 'Equipments',
        data: [60, 80, 112, 56, 33, 77]
    }],
    chart: {
        type: 'bar',
        height: 350
    },
    plotOptions: {
        bar: {
            horizontal: false,
            columnWidth: '55%',
            endingShape: 'rounded'
        },
    },
    dataLabels: {
        enabled: false
    },
    stroke: {
        show: true,
        width: 2,
        colors: ['transparent']
    },
    xaxis: {
        categories: ['Security', 'Robotics', 'Computers', 'Servers', 'HPC','Consumable' ],
    },
    yaxis: {
        title: {
            text: 'Number of Equipments'
        }
    },
    fill: {
        opacity: 1
    },
    tooltip: {
        y: {
            formatter: function (val) {
                return val + " Equipment"
            }
        }
    }
};

var chart = new ApexCharts(document.querySelector("#chart"), options);
chart.render();

//CHART1
var options = {
    series: [44, 55, 13, 43, 22],
    chart: {
        width: 380,
        type: 'pie',
    },
    labels: ['Operational', 'In Storage', 'Out of Service', 'Under Maintenance', 'Assigned'],
    responsive: [{
        breakpoint: 480,
        options: {
            chart: {
                width: 200
            },
            legend: {
                position: 'bottom'
            }
        }
    }]
};

var chart1 = new ApexCharts(document.querySelector("#chart1"), options);
chart1.render();




    function getHistory() {
    const url = "/View/Student/History";
    var token = localStorage.getItem("token")
    const headers = {
    'Content-Type': 'application/json',
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
        'Content-Type': 'application/json',
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




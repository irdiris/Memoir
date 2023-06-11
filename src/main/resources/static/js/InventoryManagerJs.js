
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


// APEXCHART
//CHART
var options = {
    series: [{
        name: 'Equipments',
        data: [security.valueOf(), robotics.valueOf(),computers.valueOf(), network.valueOf(),hpc.valueOf()]
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
        categories: ['Security', 'Robotics', 'Computers', 'Network', 'HPC' ],
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
    series: [available.valueOf(), missing.valueOf(), allocated.valueOf(), underMaintenance.valueOf(), pending.valueOf()],
    chart: {
        width: 380,
        type: 'pie',
    },
    labels: ['Available', 'Missing', 'Allocated', 'Under Maintenance', 'Pending'],
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
async function Deleteitem(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const Id = row.querySelector('td:nth-child(1)').textContent;
    console.log(Id)
    const form = new FormData()
    form.set("Id", Id);

    // Create a new Request object.
    const request = new Request("/InventoryManager/DeleteEquipment", {

        method: "POST",
        body: form,
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function DeleteResource(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const Id = row.querySelector('td:nth-child(1)').textContent;
    console.log(Id)
    const form = new FormData()
    form.set("Id", Id);

    // Create a new Request object.
    const request = new Request("/InventoryManager/DeleteResource", {

        method: "POST",
        body: form,
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}
async function DeleteResourcehistory(button) {
    var row = button.parentNode.parentNode.parentNode;
    console.log(row);
    // Get the values of the two tds.
    const Id = row.querySelector('td:nth-child(1)').textContent;
    console.log(Id)
    const form = new FormData()
    form.set("Id", Id);

    // Create a new Request object.
    const request = new Request("/InventoryManager/DeleteResourceH", {

        method: "POST",
        body: form,
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}

function  saveId(button){
    var row = button.parentNode.parentNode.parentNode;

    const Id = row.querySelector('td:nth-child(1)').textContent;
    console.log(Id);
    localStorage.setItem("serialNumber", Id );
}
function submitForm() {

    // Get the form data
    const formData = new FormData();
    const id = localStorage.getItem("serialNumber");
    formData.set("oldId", id);
    formData.set("purchases", document.getElementById("purchases").value);
    formData.set("type", document.getElementById("type").value);
    formData.set("name", document.getElementById("name").value);
    formData.set("service", document.getElementById("service").value);
    formData.set("description", document.getElementById("description").value);
    formData.set("state", document.querySelector('#state option:checked').value);

    // Submit the form
    fetch("/InventoryManager/updateItem", {
        method: "POST",
        body: formData,
    });
}
function  saveResourceId(button){
    var row = button.parentNode.parentNode.parentNode;

    const Id = row.querySelector('td:nth-child(1)').textContent;
    console.log(Id);
    localStorage.setItem("resource", Id );}
function submitFormResource() {

    // Get the form data
    const formData = new FormData();
    const id = localStorage.getItem("resource");
    formData.set("oldId", id);
    formData.set("purchases", document.getElementById("purchases").value);
    formData.set("name", document.getElementById("name").value);
    formData.set("type", document.getElementById("type").value);
    formData.set("quantity", document.getElementById("quantity").value);


    // Submit the form
    fetch("/InventoryManager/UpdateResource", {
        method: "POST",
        body: formData,
    });
}
async function TriggerInventoryCheck() {



    // Create a new Request object.
    const request = new Request("/InventoryManager/TriggerCheck", {

        method: "POST",
    });
    try {
        // Send the request.
        const response = await fetch(request);

        // Handle the response.
        if (response.status === 200) {
            // The request was successful.
            console.log("The request was successful.");
            location.reload();
        } else {
            // The request failed.
            console.log("The request failed.");
        }
    } catch (error) {
        console.log(error);
    }

}

function generatePDF() {



    // Define the styles for the report
    var styles = {
        header: {
            fontSize: 20,
            bold: true,
            alignment: 'center',
            marginBottom: 20
        },
        subheader: {
            fontSize: 16,
            bold: true,
            marginBottom: 10
        },
        sectionContent: {
            fontSize: 12,
            marginBottom: 10
        }
    };

    // Define the report content
    var content = [

        {
            text: 'Constantine University 2',
            style: 'header'
        },
        {
            text: 'Inventory Report',
            style: 'header'
        },
        {
            text: 'Missing section',
            style: 'subheader'
        },
        {
            text: missing,
            style: 'sectionContent'
        },
        {
            text: 'Pending Section',
            style: 'subheader'
        },
        {
            text: pending,
            style: 'sectionContent'
        },
        {
            text: 'Available Section',
            style: 'subheader'
        },
        {
            text: available,
            style: 'sectionContent'
        },
        {
            text: 'Under Maintenance Section',
            style: 'subheader'
        },
        {
            text: underMaintenance,
            style: 'sectionContent'
        },
        {
            text: 'Allocated Section',
            style: 'subheader'
        },
        {
            text: allocated,
            style: 'sectionContent'
        },
        {
            text: 'Security Section',
            style: 'subheader'
        },
        {
            text: security,
            style: 'sectionContent'
        },
        {
            text: 'Network Section',
            style: 'subheader'
        },
        {
            text: network,
            style: 'sectionContent'
        },
        {
            text: 'Computers Section',
            style: 'subheader'
        },
        {
            text: computers,
            style: 'sectionContent'
        },
        {
            text: 'HPC Section',
            style: 'subheader'
        },
        {
            text: hpc,
            style: 'sectionContent'
        },
        {
            text: 'Robotics Section',
            style: 'subheader'
        },
        {
            text: robotics,
            style: 'sectionContent'
        }
    ];

    // Create the document definition
    var docDefinition = {
        content: content,
        styles: styles
    };

    // Create the PDF
    pdfMake.createPdf(docDefinition).download('inventory_report.pdf');
}

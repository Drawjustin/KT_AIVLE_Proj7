// 위치 정보 관리
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            position => {
                document.getElementById('latitude').value = position.coords.latitude;
                document.getElementById('longitude').value = position.coords.longitude;
            },
            error => {
                console.error("Error getting location:", error);
                alert("위치 정보를 가져오는데 실패했습니다. 위치 권한을 확인해주세요.");
            }
        );
    }
}

// 폼 제출 처리
function initializeForm() {
    const form = document.getElementById('emergencyForm');
    const getLocationBtn = document.getElementById('getLocationBtn');

    if (getLocationBtn) {
        getLocationBtn.addEventListener('click', getLocation);
    }

    if (form) {
        form.addEventListener('submit', function(e) {
            e.preventDefault();

            const request = document.getElementById('request').value;
            const latitude = document.getElementById('latitude').value;
            const longitude = document.getElementById('longitude').value;

            if (!request || !latitude || !longitude) {
                alert('모든 필드를 채워주세요.');
                return;
            }

            // 위도/경도 유효성 검사 추가
            if (!isValidCoordinate(latitude, -90, 90) || !isValidCoordinate(longitude, -180, 180)) {
                alert('올바른 위도/경도 값을 입력해주세요.');
                return;
            }

            window.location.href = `/recommend_hospital?request=${encodeURIComponent(request)}&latitude=${latitude}&longitude=${longitude}`;
        });
    }
}

// 위도/경도 유효성 검사
function isValidCoordinate(value, min, max) {
    const num = parseFloat(value);
    return !isNaN(num) && num >= min && num <= max;
}

// 네비게이션 바 스크롤 효과
function initializeNavigation() {
    window.addEventListener('scroll', function() {
        const nav = document.querySelector('nav');
        if (window.scrollY > 50) {
            nav.classList.remove('bg-transparent');
            nav.classList.add('bg-white', 'shadow');
        } else {
            nav.classList.add('bg-transparent');
            nav.classList.remove('bg-white', 'shadow');
        }
    });
}

// 페이지 초기화
document.addEventListener('DOMContentLoaded', function() {
    initializeForm();
    initializeNavigation();
});
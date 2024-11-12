<!-- Home.vue -->
<template>
  <div class="index-page">
    <!-- 使用 Header 组件 -->
    <Header/>
    <main class="main">

      <!-- Hero Section -->
      <section id="hero" class="hero section dark-background">

        <div class="container">
          <div class="row gy-4">
            <div class="col-lg-6 order-2 order-lg-1 d-flex flex-column justify-content-center" data-aos="zoom-out">
              <h1>夏令营管理系统</h1>
              <p>管理报名、审核、活动安排全面支持夏令营的高效管理</p>
              <div class="d-flex">
                <router-link to="/login" class="btn-get-started">登录</router-link>
                <a href="https://www.youtube.com/watch?v=Y7f98aduVJ8"
                   class="glightbox btn-watch-video d-flex align-items-center"><i class="bi bi-play-circle"></i><span>Watch Video</span></a>
              </div>
            </div>
            <div class="col-lg-6 order-1 order-lg-2 hero-img" data-aos="zoom-out" data-aos-delay="200">
              <img src="assets/img/hero-img.png" class="img-fluid animated" alt="">
            </div>
          </div>
        </div>

      </section><!-- /Hero Section -->

      <!--      &lt;!&ndash; Clients Section &ndash;&gt;-->
      <!--      <section id="clients" class="clients section light-background">-->

      <!--        <div class="container" data-aos="zoom-in">-->

      <!--          <div class="swiper init-swiper">-->
      <!--            <div class="swiper-wrapper align-items-center">-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-1.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-2.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-3.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-4.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-5.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-6.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-7.png" class="img-fluid" alt=""></div>-->
      <!--              <div class="swiper-slide"><img src="assets/img/clients/client-8.png" class="img-fluid" alt=""></div>-->
      <!--            </div>-->
      <!--          </div>-->

      <!--        </div>-->

      <!--      </section>&lt;!&ndash; /Clients Section &ndash;&gt;-->

      <!-- About Section -->
      <section id="about" class="about section">

        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
          <h2>关于我们</h2>
        </div><!-- End Section Title -->

        <div class="container">

          <div class="row gy-4">

            <div class="col-lg-6 content" data-aos="fade-up" data-aos-delay="100">
              <p>
                我们是一个专注于夏令营管理系统开发的团队，致力于为夏令营活动提供全面的数字化解决方案，简化报名、活动管理和审核流程，让组织者和参与者都能获得更佳的体验。
              </p>
              <ul>
                <li><i class="bi bi-check2-circle"></i> <span>支持在线报名和多级审核，确保报名过程简便且信息准确。</span>
                </li>
                <li><i class="bi bi-check2-circle"></i>
                  <span>集成活动日程安排和提醒功能，帮助学员和家长随时了解活动安排。</span></li>
                <li><i class="bi bi-check2-circle"></i>
                  <span>提供动态数据分析和反馈，帮助主办方优化活动管理和用户体验。</span></li>
              </ul>
            </div>

            <div class="col-lg-6" data-aos="fade-up" data-aos-delay="200">
              <p>
                我们的夏令营管理系统不仅提供便捷的报名和审核流程，还支持活动日程安排、学员档案管理、通知发送等功能，确保每一项活动都能顺利开展。通过我们系统的智能化管理，组织者能够节省大量时间和精力，专注于活动本身的质量提升。
              </p>
              <a href="#" class="read-more"><span>了解更多</span><i class="bi bi-arrow-right"></i></a>
            </div>


          </div>

        </div>

      </section><!-- /About Section -->

      <!-- Why Us Section -->
      <section id="why-us" class="section why-us light-background" data-builder="section">

        <div class="container-fluid">

          <div class="row gy-4">

            <div class="col-lg-7 d-flex flex-column justify-content-center order-2 order-lg-1">

              <div class="content px-xl-5" data-aos="fade-up" data-aos-delay="100">
                <h3>夏令营管理解决方案</h3>
                <p>
                  我们的夏令营管理系统为学校和教育机构提供了全面的解决方案，从学生报名到活动安排，再到管理和反馈的各个环节，确保夏令营活动的高效和顺利开展。
                </p>
              </div>

              <div class="faq-container px-xl-5" data-aos="fade-up" data-aos-delay="200">

                <div class="faq-item faq-active">
                  <h3><span>01</span> 如何通过系统进行学生的在线报名？</h3>
                  <div class="faq-content">
                    <p>
                      我们的系统支持在线报名功能。家长或学生可以在指定时间段内通过网络平台完成报名，填写个人信息、选择活动项目，并实时查看报名状态。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

                <div class="faq-item">
                  <h3><span>02</span> 系统如何帮助安排和管理活动日程？</h3>
                  <div class="faq-content">
                    <p>
                      系统提供智能化的活动日程安排功能，可以根据参与人数和活动需求进行分组安排。活动时间表和详细日程将会自动生成并发送给相关的学生和家长，确保活动顺利进行。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

                <div class="faq-item">
                  <h3><span>03</span> 系统如何支持夏令营活动的数据分析和反馈收集？</h3>
                  <div class="faq-content">
                    <p>
                      系统会自动记录活动的各项数据，包括参与人数、活动反馈、学生表现等。主办方可以查看这些数据，分析活动效果，并根据反馈优化未来的夏令营安排。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->


              </div>

            </div>

            <div class="col-lg-5 order-1 order-lg-2 why-us-img">
              <img src="assets/img/why-us.png" class="img-fluid" alt="" data-aos="zoom-in" data-aos-delay="100">
            </div>
          </div>

        </div>

      </section><!-- /Why Us Section -->

      <!-- Skills Section -->
      <section id="skills" class="skills section">

        <div class="container" data-aos="fade-up" data-aos-delay="100">

          <div class="row">

            <div class="col-lg-6 d-flex align-items-center">
              <img src="assets/img/skills.png" class="img-fluid" alt="">
            </div>

            <div class="col-lg-6 pt-4 pt-lg-0 content">

              <h3>提供高效的夏令营管理解决方案</h3>
              <p class="fst-italic">
                我们致力于为学校和教育机构提供一个全面、可靠的夏令营管理系统，从报名管理到活动安排，帮助您轻松管理夏令营的每一个环节。
              </p>


              <div class="skills-content skills-animation">

                <div class="progress">
                  <span class="skill"><span>HTML</span> <i class="val">100%</i></span>
                  <div class="progress-bar-wrap">
                    <div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0"
                         aria-valuemax="100"></div>
                  </div>
                </div><!-- End Skills Item -->

                <div class="progress">
                  <span class="skill"><span>CSS</span> <i class="val">90%</i></span>
                  <div class="progress-bar-wrap">
                    <div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0"
                         aria-valuemax="100"></div>
                  </div>
                </div><!-- End Skills Item -->

                <div class="progress">
                  <span class="skill"><span>JavaScript</span> <i class="val">75%</i></span>
                  <div class="progress-bar-wrap">
                    <div class="progress-bar" role="progressbar" aria-valuenow="75" aria-valuemin="0"
                         aria-valuemax="100"></div>
                  </div>
                </div><!-- End Skills Item -->

                <div class="progress">
                  <span class="skill"><span>Photoshop</span> <i class="val">55%</i></span>
                  <div class="progress-bar-wrap">
                    <div class="progress-bar" role="progressbar" aria-valuenow="55" aria-valuemin="0"
                         aria-valuemax="100"></div>
                  </div>
                </div><!-- End Skills Item -->

              </div>

            </div>
          </div>

        </div>

      </section><!-- /Skills Section -->

      <!-- Services Section -->
      <section id="services" class="services section light-background">

        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
          <h2>系统特点</h2>
          <p>我们提供以下功能支持夏令营的全面管理，帮助您高效完成报名、审核和活动安排：</p>
        </div><!-- End Section Title -->

        <div class="container">

          <div class="row gy-4">

            <div class="col-xl-3 col-md-6 d-flex" data-aos="fade-up" data-aos-delay="100">
              <div class="service-item position-relative">
                <div class="icon"><i class="bi bi-activity icon"></i></div>
                <h4>报名管理</h4>
                <p>支持学生在线报名，简化报名流程并自动保存报名信息，方便后续审核和管理。</p>
              </div>
            </div><!-- End Service Item -->

            <div class="col-xl-3 col-md-6 d-flex" data-aos="fade-up" data-aos-delay="200">
              <div class="service-item position-relative">
                <div class="icon"><i class="bi bi-bounding-box-circles icon"></i></div>
                <h4>资料管理</h4>
                <p>集中管理学生和教师资料，支持信息的更新、查阅和导出，方便快速获取关键信息。</p>
              </div>
            </div><!-- End Service Item -->

            <div class="col-xl-3 col-md-6 d-flex" data-aos="fade-up" data-aos-delay="300">
              <div class="service-item position-relative">
                <div class="icon"><i class="bi bi-calendar4-week icon"></i></div>
                <h4>审核流程</h4>
                <p>自动化的报名审核流程，支持多级审核和审批记录保存，确保信息的准确性和审核流程的透明性。</p>
              </div>
            </div><!-- End Service Item -->

            <div class="col-xl-3 col-md-6 d-flex" data-aos="fade-up" data-aos-delay="400">
              <div class="service-item position-relative">
                <div class="icon"><i class="bi bi-broadcast icon"></i></div>
                <h4>活动管理</h4>
                <p>支持活动的创建、时间安排和任务分配，确保夏令营活动顺利组织、执行，并便于学生和教师的参与。</p>
              </div>
            </div><!-- End Service Item -->

          </div>


        </div>

      </section><!-- /Services Section -->

      <!-- Call To Action Section -->
      <section id="call-to-action" class="call-to-action section dark-background">

        <img src="assets/img/cta-bg.jpg" alt="">

        <div class="container">

          <div class="row" data-aos="zoom-in" data-aos-delay="100">
            <div class="col-xl-9 text-center text-xl-start">
              <h3>立即行动，加入我们的夏令营管理系统</h3>
              <p>
                我们的夏令营管理系统为您提供简化的报名流程、高效的活动管理和智能化的审核功能。让我们一起为学生们打造更丰富的夏令营体验，节省管理时间，提高工作效率！</p>
            </div>

            <div class="col-xl-3 cta-btn-container text-center">
              <a class="cta-btn align-middle" href="#">Call To Action</a>
            </div>
          </div>

        </div>

      </section><!-- /Call To Action Section -->

<!--      &lt;!&ndash; Portfolio Section &ndash;&gt;-->
<!--      <section id="portfolio" class="portfolio section">-->

<!--        &lt;!&ndash; Section Title &ndash;&gt;-->
<!--        <div class="container section-title" data-aos="fade-up">-->
<!--          <h2>Portfolio</h2>-->
<!--          <p>Necessitatibus eius consequatur ex aliquid fuga eum quidem sint consectetur velit</p>-->
<!--        </div>&lt;!&ndash; End Section Title &ndash;&gt;-->

<!--        <div class="container">-->

<!--          <div class="isotope-layout" data-default-filter="*" data-layout="masonry" data-sort="original-order">-->

<!--            <ul class="portfolio-filters isotope-filters" data-aos="fade-up" data-aos-delay="100">-->
<!--              <li data-filter="*" class="filter-active">All</li>-->
<!--              <li data-filter=".filter-app">App</li>-->
<!--              <li data-filter=".filter-product">Card</li>-->
<!--              <li data-filter=".filter-branding">Web</li>-->
<!--            </ul>&lt;!&ndash; End Portfolio Filters &ndash;&gt;-->

<!--            <div class="row gy-4 isotope-container" data-aos="fade-up" data-aos-delay="200">-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-app">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-1.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>App 1</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-1.jpg" title="App 1"-->
<!--                     data-gallery="portfolio-gallery-app" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-product">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-2.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>Product 1</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-2.jpg" title="Product 1"-->
<!--                     data-gallery="portfolio-gallery-product" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-branding">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-3.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>Branding 1</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-3.jpg" title="Branding 1"-->
<!--                     data-gallery="portfolio-gallery-branding" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-app">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-4.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>App 2</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-4.jpg" title="App 2"-->
<!--                     data-gallery="portfolio-gallery-app" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-product">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-5.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>Product 2</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-5.jpg" title="Product 2"-->
<!--                     data-gallery="portfolio-gallery-product" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-branding">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-6.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>Branding 2</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-6.jpg" title="Branding 2"-->
<!--                     data-gallery="portfolio-gallery-branding" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-app">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-7.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>App 3</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-7.jpg" title="App 3"-->
<!--                     data-gallery="portfolio-gallery-app" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-product">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-8.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>Product 3</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-8.jpg" title="Product 3"-->
<!--                     data-gallery="portfolio-gallery-product" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--              <div class="col-lg-4 col-md-6 portfolio-item isotope-item filter-branding">-->
<!--                <img src="assets/img/masonry-portfolio/masonry-portfolio-9.jpg" class="img-fluid" alt="">-->
<!--                <div class="portfolio-info">-->
<!--                  <h4>Branding 3</h4>-->
<!--                  <p>Lorem ipsum, dolor sit</p>-->
<!--                  <a href="assets/img/masonry-portfolio/masonry-portfolio-9.jpg" title="Branding 2"-->
<!--                     data-gallery="portfolio-gallery-branding" class="glightbox preview-link"><i-->
<!--                      class="bi bi-zoom-in"></i></a>-->
<!--                  <a href="portfolio-details.html" title="More Details" class="details-link"><i-->
<!--                      class="bi bi-link-45deg"></i></a>-->
<!--                </div>-->
<!--              </div>&lt;!&ndash; End Portfolio Item &ndash;&gt;-->

<!--            </div>&lt;!&ndash; End Portfolio Container &ndash;&gt;-->

<!--          </div>-->

<!--        </div>-->

<!--      </section>&lt;!&ndash; /Portfolio Section &ndash;&gt;-->

      <!-- Team Section -->
      <section id="team" class="team section">
        <!-- Testimonials Section -->
        <section id="testimonials" class="testimonials section">

          <!-- Section Title -->
          <div class="container section-title" data-aos="fade-up">
            <h2>组长</h2>
            <p>大连海事大学软件工程15组</p>
          </div><!-- End Section Title -->

          <div class="container" data-aos="fade-up" data-aos-delay="100">

            <div class="swiper init-swiper">
              <div class="swiper-wrapper">

                <div class="swiper-slide">
                  <div class="testimonial-item">
                    <img src="assets/img/testimonials/testimonials-1.jpg" class="testimonial-img" alt="">
                    <h3>开发团队</h3>
                    <h4>Ceo &amp; Founder</h4>
                    <div class="stars">
                      <i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i>
                    </div>
                    <p>
                      <i class="bi bi-quote quote-icon-left"></i>
                      <span>负责项目整体管理与协调，确保项目按计划进展，拥有丰富的项目管理经验。</span>
                      <i class="bi bi-quote quote-icon-right"></i>
                    </p>
                  </div>
                </div><!-- End testimonial item -->

                <div class="swiper-slide">
                  <div class="testimonial-item">
                    <img src="assets/img/testimonials/testimonials-2.jpg" class="testimonial-img" alt="">
                    <h3>Sara Wilsson</h3>
                    <h4>Designer</h4>
                    <div class="stars">
                      <i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i>
                    </div>
                    <p>
                      <i class="bi bi-quote quote-icon-left"></i>
                      <span>Export tempor illum tamen malis malis eram quae irure esse labore quem cillum quid cillum eram malis quorum velit fore eram velit sunt aliqua noster fugiat irure amet legam anim culpa.</span>
                      <i class="bi bi-quote quote-icon-right"></i>
                    </p>
                  </div>
                </div><!-- End testimonial item -->

                <div class="swiper-slide">
                  <div class="testimonial-item">
                    <img src="assets/img/testimonials/testimonials-3.jpg" class="testimonial-img" alt="">
                    <h3>Jena Karlis</h3>
                    <h4>Store Owner</h4>
                    <div class="stars">
                      <i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i>
                    </div>
                    <p>
                      <i class="bi bi-quote quote-icon-left"></i>
                      <span>Enim nisi quem export duis labore cillum quae magna enim sint quorum nulla quem veniam duis minim tempor labore quem eram duis noster aute amet eram fore quis sint minim.</span>
                      <i class="bi bi-quote quote-icon-right"></i>
                    </p>
                  </div>
                </div><!-- End testimonial item -->

                <div class="swiper-slide">
                  <div class="testimonial-item">
                    <img src="assets/img/testimonials/testimonials-4.jpg" class="testimonial-img" alt="">
                    <h3>Matt Brandon</h3>
                    <h4>Freelancer</h4>
                    <div class="stars">
                      <i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i>
                    </div>
                    <p>
                      <i class="bi bi-quote quote-icon-left"></i>
                      <span>Fugiat enim eram quae cillum dolore dolor amet nulla culpa multos export minim fugiat minim velit minim dolor enim duis veniam ipsum anim magna sunt elit fore quem dolore labore illum veniam.</span>
                      <i class="bi bi-quote quote-icon-right"></i>
                    </p>
                  </div>
                </div><!-- End testimonial item -->

                <div class="swiper-slide">
                  <div class="testimonial-item">
                    <img src="assets/img/testimonials/testimonials-5.jpg" class="testimonial-img" alt="">
                    <h3>John Larson</h3>
                    <h4>Entrepreneur</h4>
                    <div class="stars">
                      <i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i
                        class="bi bi-star-fill"></i>
                    </div>
                    <p>
                      <i class="bi bi-quote quote-icon-left"></i>
                      <span>Quis quorum aliqua sint quem legam fore sunt eram irure aliqua veniam tempor noster veniam enim culpa labore duis sunt culpa nulla illum cillum fugiat legam esse veniam culpa fore nisi cillum quid.</span>
                      <i class="bi bi-quote quote-icon-right"></i>
                    </p>
                  </div>
                </div><!-- End testimonial item -->

              </div>
              <div class="swiper-pagination"></div>
            </div>

          </div>

        </section><!-- /Testimonials Section -->
        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
          <h2>组员介绍</h2>
          <p>
            我们团队由夏令营管理系统领域的专家组成，专注于系统开发、用户体验优化和技术支持，致力于提供高效的夏令营管理解决方案。</p>
        </div><!-- End Section Title -->

        <div class="container">

          <div class="row gy-4">

            <div class="col-lg-6" data-aos="fade-up" data-aos-delay="100">
              <div class="team-member d-flex align-items-start">
                <div class="pic"><img src="assets/img/team/team-1.jpg" class="img-fluid" alt=""></div>
                <div class="member-info">
                  <h4>杨可心</h4>
                  <span>文档负责人</span>
                  <p>负责项目文档的撰写和整理，确保每个开发步骤和技术细节被准确记录。</p>
                  <div class="social">
                    <a href=""><i class="bi bi-twitter-x"></i></a>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
                </div>
              </div>
            </div><!-- End Team Member -->

            <div class="col-lg-6" data-aos="fade-up" data-aos-delay="200">
              <div class="team-member d-flex align-items-start">
                <div class="pic"><img src="assets/img/team/team-2.jpg" class="img-fluid" alt=""></div>
                <div class="member-info">
                  <h4>谭佳熙</h4>
                  <span>后端开发负责人</span>
                  <p>负责后端系统开发，包括API接口设计和业务逻辑实现，保障系统稳定高效。</p>
                  <div class="social">
                    <a href=""><i class="bi bi-twitter-x"></i></a>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
                </div>
              </div>
            </div><!-- End Team Member -->

            <div class="col-lg-6" data-aos="fade-up" data-aos-delay="300">
              <div class="team-member d-flex align-items-start">
                <div class="pic"><img src="assets/img/team/team-3.jpg" class="img-fluid" alt=""></div>
                <div class="member-info">
                  <h4>符镇侃</h4>
                  <span>数据库管理员</span>
                  <p>负责数据库的设计与维护，确保数据的完整性、安全性和高效访问。</p>
                  <div class="social">
                    <a href=""><i class="bi bi-twitter-x"></i></a>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
                </div>
              </div>
            </div><!-- End Team Member -->

            <div class="col-lg-6" data-aos="fade-up" data-aos-delay="400">
              <div class="team-member d-flex align-items-start">
                <div class="pic"><img src="assets/img/team/team-4.jpg" class="img-fluid" alt=""></div>
                <div class="member-info">
                  <h4>赵网</h4>
                  <span>前端开发工程师</span>
                  <p>负责前端用户界面的开发，优化用户体验，确保系统界面友好直观。</p>
                  <div class="social">
                    <a href=""><i class="bi bi-twitter-x"></i></a>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
                </div>
              </div>
            </div><!-- End Team Member -->

          </div>

        </div>

      </section><!-- /Team Section -->

      <!-- Pricing Section -->
      <section id="pricing" class="pricing section light-background">

        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
          <h2>收费标准</h2>
          <p>我们提供多种夏令营套餐，满足不同年龄段学生的需求，并确保提供优质的学习和活动体验。</p>
        </div><!-- End Section Title -->

        <div class="container">

          <div class="row gy-4">

            <div class="col-lg-4" data-aos="zoom-in" data-aos-delay="100">
              <div class="pricing-item">
                <h3>基础计划</h3>
                <h4><sup>¥</sup>0<span> / 夏令营</span></h4>
                <ul>
                  <li><i class="bi bi-check"></i> <span>报名与基础信息管理</span></li>
                  <li><i class="bi bi-check"></i> <span>课程与活动浏览</span></li>
                  <li><i class="bi bi-check"></i> <span>简单的个人信息记录</span></li>
                  <li class="na"><i class="bi bi-x"></i> <span>高级活动参与</span></li>
                  <li class="na"><i class="bi bi-x"></i> <span>专属客服支持</span></li>
                </ul>
                <a href="#" class="buy-btn">立即加入</a>
              </div>
            </div><!-- End Pricing Item -->

            <div class="col-lg-4" data-aos="zoom-in" data-aos-delay="200">
              <div class="pricing-item featured">
                <h3>进阶计划</h3>
                <h4><sup>¥</sup>299<span> / 夏令营</span></h4>
                <ul>
                  <li><i class="bi bi-check"></i> <span>所有基础功能</span></li>
                  <li><i class="bi bi-check"></i> <span>活动参与优先权</span></li>
                  <li><i class="bi bi-check"></i> <span>更多课程选择</span></li>
                  <li><i class="bi bi-check"></i> <span>健康记录与报告</span></li>
                  <li><i class="bi bi-check"></i> <span>专属客服支持</span></li>
                </ul>
                <a href="#" class="buy-btn">立即加入</a>
              </div>
            </div><!-- End Pricing Item -->

            <div class="col-lg-4" data-aos="zoom-in" data-aos-delay="300">
              <div class="pricing-item">
                <h3>高级计划</h3>
                <h4><sup>¥</sup>499<span> / 夏令营</span></h4>
                <ul>
                  <li><i class="bi bi-check"></i> <span>所有进阶功能</span></li>
                  <li><i class="bi bi-check"></i> <span>VIP 活动参与与专属课程</span></li>
                  <li><i class="bi bi-check"></i> <span>个性化活动推荐</span></li>
                  <li><i class="bi bi-check"></i> <span>全程健康监测</span></li>
                  <li><i class="bi bi-check"></i> <span>全天候专业客服支持</span></li>
                </ul>
                <a href="#" class="buy-btn">立即加入</a>
              </div>
            </div><!-- End Pricing Item -->


          </div>

        </div>

      </section><!-- /Pricing Section -->



      <!-- Faq 2 Section -->
      <section id="faq-2" class="faq-2 section light-background">

        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
          <h2>常见问题解答</h2>
          <p>我们汇总了一些关于夏令营管理系统的常见问题，帮助您更好地理解系统功能和操作流程。</p>
        </div><!-- End Section Title -->

        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-10">
              <div class="faq-container">

                <div class="faq-item faq-active" data-aos="fade-up" data-aos-delay="200">
                  <i class="faq-icon bi bi-question-circle"></i>
                  <h3>1. 如何注册和登录系统？</h3>
                  <div class="faq-content">
                    <p>点击“注册”按钮，填写个人信息完成注册。注册成功后，您可以使用您的用户名和密码进行登录。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

                <div class="faq-item" data-aos="fade-up" data-aos-delay="300">
                  <i class="faq-icon bi bi-question-circle"></i>
                  <h3>2. 报名夏令营活动的流程是什么？</h3>
                  <div class="faq-content">
                    <p>登录系统后，进入“活动报名”页面，选择您感兴趣的活动并提交报名表。提交后，您可以在“报名状态”页面查看审核进度。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

                <div class="faq-item" data-aos="fade-up" data-aos-delay="400">
                  <i class="faq-icon bi bi-question-circle"></i>
                  <h3>3. 如何查看和管理我的报名信息？</h3>
                  <div class="faq-content">
                    <p>在个人中心中，您可以查看您的报名历史和当前活动报名状态。如需修改报名信息，请在活动开始前联系系统管理员。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

                <div class="faq-item" data-aos="fade-up" data-aos-delay="500">
                  <i class="faq-icon bi bi-question-circle"></i>
                  <h3>4. 审核未通过的原因有哪些？</h3>
                  <div class="faq-content">
                    <p>审核未通过的原因可能包括信息填写不完整、未达到活动要求等。具体原因会在审核结果中显示，请按照提示修改并重新提交。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

                <div class="faq-item" data-aos="fade-up" data-aos-delay="600">
                  <i class="faq-icon bi bi-question-circle"></i>
                  <h3>5. 我可以取消已经提交的报名吗？</h3>
                  <div class="faq-content">
                    <p>您可以在活动报名页面中取消已提交的申请，但在活动开始前的一定时间内不可取消，具体规定请参考每个活动的报名详情。</p>
                  </div>
                  <i class="faq-toggle bi bi-chevron-right"></i>
                </div><!-- End Faq item-->

              </div>
            </div>
          </div>
        </div>


      </section><!-- /Faq 2 Section -->

      <!-- 联系我们部分 -->
      <section id="contact" class="contact section">

        <!-- 部分标题 -->
        <div class="container section-title" data-aos="fade-up">
          <h2>联系我们</h2>
          <p>如果您对夏令营系统有任何疑问或需求，欢迎通过以下方式联系我们。</p>
        </div><!-- 结束部分标题 -->

        <div class="container" data-aos="fade-up" data-aos-delay="100">
          <div class="row gy-4">

            <!-- 联系信息 -->
            <div class="col-lg-5">
              <div class="info-wrap">
                <div class="info-item d-flex" data-aos="fade-up" data-aos-delay="200">
                  <i class="bi bi-geo-alt flex-shrink-0"></i>
                  <div>
                    <h3>地址</h3>
                    <p>辽宁省大连市大连海事大学</p>
                  </div>
                </div><!-- 结束信息项 -->

                <div class="info-item d-flex" data-aos="fade-up" data-aos-delay="300">
                  <i class="bi bi-telephone flex-shrink-0"></i>
                  <div>
                    <h3>联系电话</h3>
                    <p>+86 155 5465 7267</p>
                  </div>
                </div><!-- 结束信息项 -->

                <div class="info-item d-flex" data-aos="fade-up" data-aos-delay="400">
                  <i class="bi bi-envelope flex-shrink-0"></i>
                  <div>
                    <h3>电子邮箱</h3>
                    <p>mua@dlmu.edu.cn</p>
                  </div>
                </div><!-- 结束信息项 -->

                <!-- 地图嵌入 -->
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3106.358410516237!2d121.53156607687795!3d38.87004097172987!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x358f6406d8a093ad%3A0xcdbd138d8453da8e!2z5aSn6L-e5rW35LqL5aSn5a2m!5e0!3m2!1szh-CN!2shk!4v1731371885009!5m2!1szh-CN!2shk"
                        frameborder="0" style="border:0; width: 100%; height: 270px;" allowfullscreen=""
                        loading="lazy"></iframe>
              </div>
            </div><!-- 结束联系信息 -->

            <!-- 联系表单 -->
            <div class="col-lg-7">
              <form action="forms/contact.php" method="post" class="php-email-form" data-aos="fade-up"
                    data-aos-delay="200">
                <div class="row gy-4">

                  <div class="col-md-6">
                    <label for="name-field" class="pb-2">姓名</label>
                    <input type="text" name="name" id="name-field" class="form-control" required="">
                  </div>

                  <div class="col-md-6">
                    <label for="email-field" class="pb-2">电子邮箱</label>
                    <input type="email" class="form-control" name="email" id="email-field" required="">
                  </div>

                  <div class="col-md-12">
                    <label for="subject-field" class="pb-2">主题</label>
                    <input type="text" class="form-control" name="subject" id="subject-field" required="">
                  </div>

                  <div class="col-md-12">
                    <label for="message-field" class="pb-2">留言</label>
                    <textarea class="form-control" name="message" rows="10" id="message-field" required=""></textarea>
                  </div>

                  <div class="col-md-12 text-center">
                    <div class="loading">加载中</div>
                    <div class="error-message"></div>
                    <div class="sent-message">您的消息已发送，谢谢！</div>
                    <button type="submit">发送信息</button>
                  </div>

                </div>
              </form>
            </div><!-- 结束联系表单 -->

          </div>
        </div>
      </section><!-- /联系我们部分 -->


    </main>
    <!-- 使用 Footer 组件 -->
    <Footer/>
  </div>
</template>

<script>
import Swiper from 'swiper';
import AOS from 'aos';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'aos/dist/aos.css';

export default {
  name: 'Home',
  data() {
    return {
      swiperConfig1: {
        loop: true,
        speed: 600,
        autoplay: {
          delay: 5000
        },
        slidesPerView: 'auto',
        pagination: {
          el: '.swiper-pagination',
          type: 'bullets',
          clickable: true
        },
        breakpoints: {
          320: {
            slidesPerView: 2,
            spaceBetween: 40
          },
          480: {
            slidesPerView: 3,
            spaceBetween: 60
          },
          640: {
            slidesPerView: 4,
            spaceBetween: 80
          },
          992: {
            slidesPerView: 5,
            spaceBetween: 120
          },
          1200: {
            slidesPerView: 6,
            spaceBetween: 120
          }
        }
      },
      swiperConfig2: {
        loop: true,
        speed: 600,
        autoplay: {
          delay: 5000
        },
        slidesPerView: 'auto',
        pagination: {
          el: '.swiper-pagination',
          type: 'bullets',
          clickable: true
        }
      }
    };
  },
  mounted() {
    // 初始化第一个 swiper
    new Swiper('.swiper-1', this.swiperConfig1);

    // 初始化第二个 swiper
    new Swiper('.swiper-2', this.swiperConfig2);

    // 初始化 AOS
    AOS.init({duration: 1000});
  }
};
</script>

<style>
/* 其它样式... */
</style>

